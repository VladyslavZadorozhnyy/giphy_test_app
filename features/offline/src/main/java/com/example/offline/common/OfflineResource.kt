package com.example.offline.common

sealed class OfflineResource<T>(
    val data: T? = null,
    val isLoading: Boolean = false
) {
    class Success<T>(data: T) : OfflineResource<T>(data)
    class Loading<T> : OfflineResource<T>(isLoading = true)
}