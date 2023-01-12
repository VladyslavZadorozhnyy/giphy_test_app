package com.example.giphytestapp.domain.usecase.get

import com.example.giphytestapp.common.Resource
import com.example.giphytestapp.domain.model.GifModel
import com.example.giphytestapp.domain.repository.GifRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent.inject
import retrofit2.HttpException
import java.io.IOException

class GetGifsUseCase {
    private val gifRepository: GifRepository by inject(GifRepository::class.java)

    operator fun  invoke(searchQuery: String): Flow<Resource<List<GifModel>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val gifs = gifRepository.getGifs(searchQuery)
                emit(Resource.Success(gifs))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error happened"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server, check your internet connection"))
            }
        }
    }
}