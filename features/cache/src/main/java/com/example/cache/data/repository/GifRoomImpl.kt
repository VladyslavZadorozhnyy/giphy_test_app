package com.example.cache.data.repository

import com.example.cache.common.GifEntity
import com.example.cache.data.room.GifDao
import com.example.cache.domain.repository.GifRoomRepository

class GifRoomImpl(
    private val dao: GifDao
) : GifRoomRepository {

    override suspend fun addToCache(entity: GifEntity) {
        dao.addToCache(entity)
    }

    override suspend fun removeFromCache(entity: GifEntity) {
        dao.removeFromCache(entity.hash)
    }

    override suspend fun isCached(entity: GifEntity): Boolean {
        val cachedItem = getByHash(entity.hash)
        return cachedItem != null && cachedItem.cached
    }

    override suspend fun clearCache() {
        dao.clearCache()
    }

    override suspend fun getByHash(hash: String): GifEntity? {
        return dao.getByHash(hash)
    }

    override suspend fun getCachedQueries(): List<String> {
        return dao.getCachedQueries() ?: listOf()
    }

    override suspend fun getAllGifs(): List<GifEntity> {
        return dao.getAll() ?: listOf()
    }

    override suspend fun getBySearchQuery(searchQuery: String): List<GifEntity> {
        return dao.getBySearchQuery(searchQuery) ?: listOf()
    }
}