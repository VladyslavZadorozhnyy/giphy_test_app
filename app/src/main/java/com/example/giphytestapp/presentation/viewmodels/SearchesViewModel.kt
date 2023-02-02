package com.example.giphytestapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.offline.common.OfflineResource
import com.example.offline.domain.usecase.UploadSearchQueriesUseCase
import com.example.offline.presentation.states.SearchesState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SearchesViewModel(
    private val uploadSearchQueriesUseCase: UploadSearchQueriesUseCase
) : ViewModel() {
    private val _queriesState = MutableLiveData(SearchesState())
    val queriesState: LiveData<SearchesState> = _queriesState

    fun navigateToCollection(searchQuery: String) {
        _queriesState.value = SearchesState(_queriesState.value?.searchQueries ?: listOf(),
            isLoading = false, chosenQuery = searchQuery)
    }

    fun uploadSearchQueries() {
        uploadSearchQueriesUseCase().onEach { result ->
            when (result) {
                is  OfflineResource.Success -> {
                    _queriesState.value = SearchesState(result.data ?: listOf(),
                        isLoading = false, chosenQuery = "")
                }
                is  OfflineResource.Loading -> {
                    _queriesState.value = SearchesState(isLoading = true, chosenQuery = "")
                }
            }
        }.launchIn(viewModelScope)
    }
}