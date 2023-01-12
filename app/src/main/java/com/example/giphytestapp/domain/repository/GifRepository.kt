package com.example.giphytestapp.domain.repository

import com.example.giphytestapp.domain.model.GifModel
import java.nio.ByteBuffer

interface GifRepository {
    suspend fun getGifs(searchQuery: String): List<GifModel>

    suspend fun addToCache(model: GifModel, searchQuery: String, byteBuffer: ByteBuffer?)

    suspend fun removeFromCache(model: GifModel, searchQuery: String)

    suspend fun clearCache()

    suspend fun getByHash(hash: String): GifModel?

    suspend fun getAll(): List<GifModel>

    suspend fun getBySearchQuery(searchQuery: String): List<GifModel>

    suspend fun getCachedQueries(): List<String>
}