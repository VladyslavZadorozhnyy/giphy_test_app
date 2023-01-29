package com.example.giphytestapp.presentation.states

import com.example.giphytestapp.domain.model.GifModel

data class CollectionState(
    val gifs: List<GifModel> = listOf(),
    val searchQuery: String = "",
    val currentPage: Int = 0,
    val totalPages: Int = 0,
    val error: String = ""
)