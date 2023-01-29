package com.example.cache.domain.repository

import com.example.cache.common.GifEntity

interface GifRoomRepository {
    suspend fun addToCache(entity: GifEntity)

    suspend fun removeFromCache(entity: GifEntity)

    suspend fun isCached(entity: GifEntity): Boolean

    suspend fun clearCache()

    suspend fun getByHash(hash: String): GifEntity?

    suspend fun getAllGifs(): List<GifEntity>

    suspend fun getBySearchQuery(searchQuery: String): List<GifEntity>

    suspend fun getCachedQueries(): List<String>
}