package com.example.cache.data.repository

import com.example.cache.common.GifEntity
import com.example.cache.domain.repository.GifRoomRepository
import com.example.cache.data.room.GifRoomDatabase

class GifRoomImpl(
    private val database: GifRoomDatabase?,
) : GifRoomRepository {
    private val dao by lazy { database?.gifDao() }

    override suspend fun addToCache(entity: GifEntity) {
        if (getByHash(entity.hash) == null) {
            dao?.addToCache(entity)
        }
    }

    override suspend fun removeFromCache(entity: GifEntity) {
        dao?.removeFromCache(entity.hash)
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