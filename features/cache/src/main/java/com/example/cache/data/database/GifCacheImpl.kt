package com.example.cache.data.database

import com.example.cache.data.common.GifEntity
import com.example.cache.data.database.room.GifRoomDatabase
import com.example.cache.data.storage.GifStorage
import java.nio.ByteBuffer

class GifCacheImpl(
    private val database: GifRoomDatabase?,
    private val storage: GifStorage
) : GifCache {
    private val dao by lazy { database?.gifDao() }

    override suspend fun addToCache(entity: GifEntity, byteBuffer: ByteBuffer?) {
        if (getByHash(entity.hash) == null) {
            dao?.addToCache(entity)
            storage.addToStorage(entity.filePath, byteBuffer)
        }
    }

    override suspend fun removeFromCache(entity: GifEntity) {
        dao?.removeFromCache(entity.hash)
        storage.removeFromStorage(entity.filePath)
    }

    override suspend fun isCached(entity: GifEntity): Boolean {
        val cachedItem = getByHash(entity.hash)
        return cachedItem != null && cachedItem.cached
    }

    override suspend fun clearCache() {
        dao?.clearCache()
    }

    override suspend fun getByHash(hash: String): GifEntity? {
        return dao?.getByHash(hash)
    }

    override suspend fun getCachedQueries(): List<String> {
        return dao?.getCachedQueries() ?: listOf()
    }

    override suspend fun getAllGifs(): List<GifEntity> {
        return dao?.getAll() ?: listOf()
    }

    override suspend fun getBySearchQuery(searchQuery: String): List<GifEntity> {
        return dao?.getBySearchQuery(searchQuery) ?: listOf()
    }
}