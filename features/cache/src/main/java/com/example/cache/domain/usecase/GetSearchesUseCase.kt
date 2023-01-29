package com.example.cache.domain.usecase

import com.example.cache.domain.repository.GifRoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSearchesUseCase(
    private val roomRepo: GifRoomRepository
) {
    operator fun invoke(): Flow<List<String>> = flow {
        val searches = roomRepo.getCachedQueries()
        emit(searches)
    }
}