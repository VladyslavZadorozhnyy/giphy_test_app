package com.example.offline.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.offline.domain.usecase.ObserveNetworkUseCase
import org.koin.java.KoinJavaComponent

class NetworkViewModel : ViewModel() {
    private val _networkState = MutableLiveData(false)
    val networkState: LiveData<Boolean> = _networkState

    private val observeNetworkUseCase: ObserveNetworkUseCase by KoinJavaComponent.inject(
        ObserveNetworkUseCase::class.java
    )

    private fun updateState(newOnlineValue: Boolean) {
        if (_networkState.value != newOnlineValue) { _networkState.postValue(newOnlineValue) }
    }

    fun startNetworkObserving() {
        observeNetworkUseCase(
            onAvailableCallback = { updateState(true) },
            onLostCallback = { updateState(false) }
        )
    }
}