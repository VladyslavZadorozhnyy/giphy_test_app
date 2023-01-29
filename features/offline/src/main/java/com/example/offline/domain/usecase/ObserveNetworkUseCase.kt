package com.example.offline.domain.usecase

import com.example.offline.data.network.NetworkListener

class ObserveNetworkUseCase(
    private val networkListener: NetworkListener
) {
    operator fun invoke(
        onAvailableCallback: () -> Unit,
        onLostCallback: () -> Unit,
    ) {
        networkListener.setOnAvailableCallback(onAvailableCallback)
        networkListener.setOnLostCallback(onLostCallback)
    }
}