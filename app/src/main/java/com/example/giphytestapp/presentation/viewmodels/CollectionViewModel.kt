package com.example.giphytestapp.presentation.viewmodels

import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.cache.domain.usecase.CacheGifUseCase
import com.example.cache.domain.usecase.RemoveGifUseCase
import com.example.giphytestapp.common.Resource
import com.example.giphytestapp.domain.model.GifModel
import com.example.giphytestapp.domain.model.toGifEntity
import com.example.giphytestapp.domain.usecase.GetGifsUseCase
import com.example.giphytestapp.domain.usecase.UploadGifsUseCase
import com.example.giphytestapp.presentation.states.CollectionState
import com.example.giphytestapp.presentation.ui.fragments.ProgressFragment
import com.example.giphytestapp.presentation.ui.fragments.TextMessageFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class CollectionViewModel : ViewModel() {
    private val _collectionState = MutableLiveData(CollectionState())
    val collectionState: LiveData<CollectionState> = _collectionState

    private val getGifsUseCase: GetGifsUseCase by inject(GetGifsUseCase::class.java)
    private val cacheGifUseCase: CacheGifUseCase by inject(CacheGifUseCase::class.java)
    private val removeGifUseCase: RemoveGifUseCase by inject(RemoveGifUseCase::class.java)
    private val uploadGifsUseCase: UploadGifsUseCase by inject(UploadGifsUseCase::class.java)

    private fun processNewSearch(searchQuery: String, online: Boolean, navigationViewModel: NavigationViewModel) {
        getGifsUseCase(
            searchQuery = searchQuery,
            queryPage = 0,
            online = online,
            queryIsNew = true,
            currentGifs = listOf()
        ).onEach { result ->
            when(result) {
                is  Resource.Success -> {
                    navigationViewModel.removeLastFragment()
                    _collectionState.value = CollectionState(
                        gifs = result.data ?: listOf(),
                        searchQuery = searchQuery,
                        currentPage = 1,
                        totalPages = result.pagesNumber ?: 1,
                    )
                }
                is  Resource.Loading -> {
                    navigationViewModel.navigateTo(ProgressFragment(), null)
                }
                is  Resource.Error -> {
                    navigationViewModel.removeLastFragment()
                    navigationViewModel.navigateTo(TextMessageFragment(), Bundle().also {
                        it.putString(TextMessageFragment.MESSAGE_KEY, result.message)
                    })
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun processSameSearch(online: Boolean, navigationViewModel: NavigationViewModel) {
        _collectionState.value?.let {
            if (it.currentPage > it.totalPages) { return }
        }

        getGifsUseCase(
            searchQuery = _collectionState.value?.searchQuery ?: "",
            online = online,
            queryIsNew = false,
            queryPage = _collectionState.value?.currentPage ?: 0,
            currentGifs = _collectionState.value?.gifs ?: listOf()
        ).onEach { result ->
            when(result) {
                is  Resource.Success -> {
                    _collectionState.value = CollectionState(
                        gifs = result.data ?: listOf(),
                        searchQuery = _collectionState.value?.searchQuery ?: "",
                        currentPage = _collectionState.value?.currentPage?.plus(1) ?: 1,
                        totalPages = result.pagesNumber ?: 0,
                    )
                }
                is  Resource.Loading -> {
                    navigationViewModel.navigateTo(ProgressFragment(), null)
                }
                is  Resource.Error -> {
                    navigationViewModel.removeLastFragment()
                    navigationViewModel.navigateTo(TextMessageFragment(), Bundle().also {
                        it.putString(TextMessageFragment.MESSAGE_KEY, result.message)
                    })
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getGifs(
        searchQuery: String,
        queryIsNew: Boolean,
        online: Boolean,
        navigationModel: NavigationViewModel
    ) {
        if (queryIsNew) {
            processNewSearch(searchQuery, online, navigationModel)
        } else {
            processSameSearch(online, navigationModel)
        }
    }

    fun removeGif(position: Int) {
        val curGifs = _collectionState.value?.gifs ?: return
        val removedGif = curGifs[position]
        val searchQuery = _collectionState.value?.searchQuery ?: ""

        _collectionState.value = CollectionState(
            gifs = curGifs.subList(0, position) + curGifs.subList(position + 1, curGifs.size),
            searchQuery = searchQuery,
            currentPage = _collectionState.value?.currentPage ?: 0,
            totalPages = _collectionState.value?.totalPages ?: 0,
        )

        viewModelScope.launch(Dispatchers.IO) {
            removeGifUseCase(removedGif.toGifEntity(searchQuery))
        }
    }

    fun loadGifToView(model: GifModel, imageView: ImageView, placeholder: CircularProgressDrawable) {
        val searchValue = _collectionState.value?.searchQuery ?: return

        uploadGifsUseCase(model.url, imageView, placeholder) { byteBuffer ->
            viewModelScope.launch(Dispatchers.IO) {
                cacheGifUseCase(model.toGifEntity(searchValue), byteBuffer)
            }
        }
    }
}