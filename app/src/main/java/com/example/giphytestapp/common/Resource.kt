package com.example.giphytestapp.common

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val pagesNumber: Int? = null
) {
    class Success<T>(data: T, pagesNumber: Int?) : Resource<T>(data, pagesNumber = pagesNumber)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message = message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}