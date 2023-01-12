package com.example.giphytestapp.data.repository

import com.example.cache.data.database.GifCache
import com.example.giphytestapp.data.remote.GiphyAPI
import com.example.giphytestapp.domain.model.GifModel
import com.example.giphytestapp.domain.model.fromGifDto
import com.example.giphytestapp.domain.model.fromGifEntity
import com.example.giphytestapp.domain.model.toGifEntity
import com.example.giphytestapp.domain.repository.GifRepository
import java.nio.ByteBuffer

class GifRepositoryImpl(
    private val apiService: GiphyAPI,
    private val cacheService: GifCache
) : GifRepository {
    override suspend fun getGifs(searchQuery: String): List<GifModel> {
        return apiService.getGifs(searchQuery).data.map {
            GifModel.fromGifDto(it.images.gifDtoOriginal)
        }
    }

    override suspend fun addToCache(model: GifModel, searchQuery: String, byteBuffer: ByteBuffer?) {
        cacheService.addToCache(model.toGifEntity(searchQuery), byteBuffer)
    }

    override suspend fun removeFromCache(model: GifModel, searchQuery: String) {
        cacheService.removeFromCache(model.toGifEntity(searchQuery))
    }

    override suspend fun clearCache() {
        cacheService.clearCache()
    }

    override suspend fun getByHash(hash: String): GifModel? {
        cacheService.getByHash(hash)?.let {
            GifModel.fromGifEntity(it)
        }
        return null
    }

    override suspend fun getAll(): List<GifModel> {
        return cacheService.getAllGifs().map {
            GifModel.fromGifEntity(it)
        }
    }

    override suspend fun getBySearchQuery(searchQuery: String): List<GifModel> {
        return cacheService.getBySearchQuery(searchQuery).map {
            GifModel.fromGifEntity(it)
        }
    }

    override suspend fun getCachedQueries(): List<String> {
        return cacheService.getCachedQueries()
    }
}