package com.example.giphytestapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.offline.presentation.viewmodels.NetworkViewModel

class AppViewModel(
    val networkViewModel: NetworkViewModel,
    val navigationViewModel: NavigationViewModel,
    val collectionViewModel: CollectionViewModel,
) : ViewModel() {
    init {
        networkViewModel.startNetworkObserving()
    }

    fun processSearchQuery(searchQuery: String = "", queryIsNew: Boolean, online: Boolean) {
        collectionViewModel.getGifs(searchQuery, queryIsNew, online, navigationViewModel)
    }
}