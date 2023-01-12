package com.example.cache.data.database

import com.example.cache.data.common.GifEntity
import java.nio.ByteBuffer

interface GifCache {
    suspend fun addToCache(entity: GifEntity, byteBuffer: ByteBuffer?)

    suspend fun removeFromCache(entity: GifEntity)

    suspend fun isCached(entity: GifEntity): Boolean

    suspend fun clearCache()

    suspend fun getByHash(hash: String): GifEntity?

    suspend fun getAllGifs(): List<GifEntity>

    suspend fun getBySearchQuery(searchQuery: String): List<GifEntity>

    suspend fun getCachedQueries(): List<String>
}