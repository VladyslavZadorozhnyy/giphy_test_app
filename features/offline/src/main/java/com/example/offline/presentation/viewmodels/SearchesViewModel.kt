package com.example.offline.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.offline.common.OfflineResource
import org.koin.java.KoinJavaComponent.inject
import com.example.offline.domain.usecase.UploadSearchQueriesUseCase
import com.example.offline.presentation.states.SearchesState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SearchesViewModel : ViewModel() {
    private val uploadSearchQueriesUseCase: UploadSearchQueriesUseCase by inject(UploadSearchQueriesUseCase::class.java)
    private var contract: Contract? = null

    private val _queriesState = MutableLiveData(SearchesState())
    val queriesState: LiveData<SearchesState> = _queriesState

    fun exitFromApplication() { contract?.exitApp() }

    fun goToOfflineScreen() { uploadSearchQueries() }

    fun setContract(value: Contract) { if (contract == null) { contract = value } }

    fun navigateToCollection(searchQuery: String) { contract?.navigateToCollection(searchQuery) }

    private fun navigateToLoading() { contract?.navigateToLoading() }

    private fun removeLastFragment() { contract?.removeLastFragment() }

    private fun uploadSearchQueries() {
        contract?.navigateToOffline()

        uploadSearchQueriesUseCase().onEach { result ->
            when(result) {
                is  OfflineResource.Success -> {
                    removeLastFragment()
                    _queriesState.value = SearchesState(result.data ?: listOf())
                }
                is  OfflineResource.Loading -> {
                    navigateToLoading()
                }
            }
        }.launchIn(viewModelScope)
    }

    interface Contract {
        fun exitApp()

        fun navigateToOffline()

        fun navigateToLoading()

        fun navigateToCollection(searchQuery: String)

        fun removeLastFragment()
    }
}