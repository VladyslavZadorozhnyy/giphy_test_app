package com.example.cache.domain.usecase

import com.example.cache.common.GifEntity
import com.example.cache.common.generateFilename
import com.example.cache.domain.repository.GifRoomRepository
import com.example.cache.domain.repository.GifStorageRepository

class RemoveGifUseCase(
    private val dbRepo: GifRoomRepository,
    private val storageRepo: GifStorageRepository,
) {
    suspend operator fun invoke(entity: GifEntity) {
        if (dbRepo.getByHash(entity.hash) != null) {
            dbRepo.removeFromCache(entity)
            storageRepo.removeFromStorage(entity.generateFilename())
        }
    }
}