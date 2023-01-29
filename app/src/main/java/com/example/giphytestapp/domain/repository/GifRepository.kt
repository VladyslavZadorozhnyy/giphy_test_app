package com.example.giphytestapp.domain.repository

import com.example.giphytestapp.domain.model.GifModel

interface GifRepository {
    suspend fun getGifs(searchQuery: String, currentPage: Int): List<GifModel>

    suspend fun getPagesNumber(searchQuery: String): Int
}