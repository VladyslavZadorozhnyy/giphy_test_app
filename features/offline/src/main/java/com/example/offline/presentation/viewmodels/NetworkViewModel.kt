package com.example.offline.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.offline.domain.usecase.ObserveNetworkUseCase

class NetworkViewModel(
    private val observeNetworkUseCase: ObserveNetworkUseCase
) : ViewModel() {
    private val _networkState = MutableLiveData(false)
    val stateOnline: LiveData<Boolean> = _networkState

    init {
        startNetworkObserving()
    }

    private fun updateState(newOnlineValue: Boolean) {
        if (_networkState.value != newOnlineValue) { _networkState.postValue(newOnlineValue) }
    }

    private fun startNetworkObserving() {
        observeNetworkUseCase(
            onAvailableCallback = { updateState(true) },
            onLostCallback = { updateState(false) }
        )
    }
}