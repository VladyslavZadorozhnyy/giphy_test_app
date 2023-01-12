package com.example.giphytestapp.presentation.common

import com.example.giphytestapp.domain.model.GifModel

data class CollectionScreenState(
    val isLoading: Boolean = false,
    val gifs: List<GifModel> = listOf(),
    val lastSearchQuery: String = "",
    val currentPage: Int = 0,
    val error: String = ""
)