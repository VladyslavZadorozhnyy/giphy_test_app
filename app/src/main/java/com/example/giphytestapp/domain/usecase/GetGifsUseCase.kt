package com.example.giphytestapp.domain.usecase

import com.example.cache.domain.repository.GifRoomRepository
import com.example.giphytestapp.common.Constants.LOADING_ITEM
import com.example.giphytestapp.common.Resource
import com.example.giphytestapp.domain.model.GifModel
import com.example.giphytestapp.domain.repository.GifRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetGifsUseCase (
    private val apiRepository: GifRepository,
    private val roomRepository: GifRoomRepository
) {
    operator fun  invoke(
        searchQuery: String,
        queryIsNew: Boolean,
        online: Boolean,
        queryPage: Int,
        currentGifs: List<GifModel>
    ): Flow<Resource<List<GifModel>>> {
        return flow {
            try {
                if (queryIsNew) {
                    emit(Resource.Loading())
                } else {
                    emit(Resource.Success(data = currentGifs + LOADING_ITEM, pagesNumber = 0))
                }

                delay(7000)
                val newGifs = withContext(Dispatchers.IO) {
                    if (online) {
                        apiRepository.getGifs(searchQuery, queryPage)
                    } else {
                        roomRepository.getBySearchQuery(searchQuery).map { GifModel.fromGifEntity(it) }
                    }
                }

                val pagesCount = withContext(Dispatchers.IO) {
                    if (online) {
                        apiRepository.getPagesNumber(searchQuery)
                    } else { 0 }
                }

                emit(Resource.Success(data = currentGifs + newGifs, pagesNumber = pagesCount))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error happened"))
            } catch (e: IOException) {
                emit(Resource.Error("Can't reach server, check your internet connection"))
            } catch (e: Throwable) {
                emit(Resource.Error("Unknown error happened: ${e.message}"))
            }
        }
    }
}