package com.example.offline.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build


class NetworkListener(context: Context) : ConnectivityManager.NetworkCallback() {
    private var alreadySetUp = false

    private var onAvailableCallback: (() -> Unit)? = null
    private var onLostCallback: (() -> Unit)? = null

    private val conManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    init {
        setInitialState()
    }

    override fun onUnavailable() {
        super.onUnavailable()
        updateState()
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        updateState()
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        updateState()
    }

    private fun setInitialState() {
        if (!alreadySetUp) {
            conManager.registerNetworkCallback(networkRequest, this)
            alreadySetUp = true
            updateState()
        }
    }

    private fun updateState() {
        if (deviceOnline()) {
            onAvailableCallback?.invoke()
        } else {
            onLostCallback?.invoke()
        }
    }

    private fun deviceOnline(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val netCap = conManager.getNetworkCapabilities(conManager.activeNetwork)
            netCap != null
        } else {
            val activeNetwork = conManager.activeNetworkInfo
            activeNetwork?.isConnectedOrConnecting == true && activeNetwork.isAvailable
        }
    }

    fun setOnAvailableCallback(value: () -> Unit) {
        onAvailableCallback = value
    }

    fun setOnLostCallback(value: () -> Unit) {
        onLostCallback = value
    }
}