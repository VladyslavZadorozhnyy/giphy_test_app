package com.example.offline.domain.usecase

import android.util.Log
import com.example.cache.domain.repository.GifRoomRepository
import com.example.offline.common.OfflineResource
import com.example.offline.domain.model.SearchQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class UploadSearchQueriesUseCase(
    private val roomRepo: GifRoomRepository
) {
    operator fun invoke(): Flow<OfflineResource<List<SearchQuery>>> =
        flow {
            emit(OfflineResource.Loading())
            delay(7000)

            val newData = withContext(Dispatchers.IO) {
                roomRepo.getCachedQueries().map { queryValue -> SearchQuery(queryValue) }
            }

            emit(OfflineResource.Success(data = newData))
        }
    }