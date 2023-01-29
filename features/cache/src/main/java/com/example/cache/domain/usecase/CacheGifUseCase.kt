package com.example.cache.domain.usecase

import com.example.cache.common.GifEntity
import com.example.cache.common.generateFilename
import com.example.cache.domain.repository.GifRoomRepository
import com.example.cache.domain.repository.GifStorageRepository
import java.nio.ByteBuffer

class CacheGifUseCase(
    private val dbRepo: GifRoomRepository,
    private val storageRepo: GifStorageRepository,
) {
    suspend operator fun invoke(entity: GifEntity, buffer: ByteBuffer) {
        if (dbRepo.getByHash(entity.hash) == null) {
            storageRepo.addToStorage(entity.generateFilename(), buffer)
            dbRepo.addToCache(entity)
        }
    }
}