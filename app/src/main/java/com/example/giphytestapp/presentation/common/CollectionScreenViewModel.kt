package com.example.giphytestapp.presentation.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giphytestapp.common.Resource
import com.example.giphytestapp.domain.usecase.get.GetGifsUseCase
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class CollectionScreenViewModel : ViewModel() {
    private val getGifsUseCase: GetGifsUseCase by inject(GetGifsUseCase::class.java)

    private val _state = MutableLiveData(CollectionScreenState())
    val state: LiveData<CollectionScreenState> = _state

    fun getGifs(searchQuery: String) {
        viewModelScope.launch {
            getGifsUseCase(searchQuery).onEach { result ->
                when(result) {
                    is  Resource.Success -> {
                        _state.value = CollectionScreenState(
                            gifs = result.data ?: listOf()
                        )
                    }
                    is  Resource.Loading -> {
                        _state.value = CollectionScreenState(
                            isLoading = true
                        )
                    }
                    is  Resource.Error -> {
                        _state.value = CollectionScreenState(
                            error = result.message ?: "An unexpected error happened"
                        )
                    }
                }
            }
        }
    }
}