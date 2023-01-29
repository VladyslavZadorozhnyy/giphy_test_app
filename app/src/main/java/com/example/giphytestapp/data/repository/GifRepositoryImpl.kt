package com.example.giphytestapp.data.repository

import com.example.cache.domain.repository.GifRoomRepository
import com.example.giphytestapp.common.Constants
import com.example.giphytestapp.data.remote.GiphyAPI
import com.example.giphytestapp.domain.model.GifModel
import com.example.giphytestapp.domain.model.fromGifDto
import com.example.giphytestapp.domain.repository.GifRepository
import kotlin.math.ceil

class GifRepositoryImpl(
    private val apiService: GiphyAPI,
    private val cacheService: GifRoomRepository
) : GifRepository {
    override suspend fun getGifs(searchQuery: String, currentPage: Int): List<GifModel> {
        return apiService.getGifs(
            searchQuery = searchQuery,
            offset = Constants.PAGE_SIZE * currentPage
        ).data.map {
            GifModel.fromGifDto(it.images.gifDtoOriginal)
        }.filter {
            cacheService.getByHash(it.hash)?.cached ?: true
        }.toList()
    }

    override suspend fun getPagesNumber(searchQuery: String): Int {
        val itemsCount = apiService.getGifs(searchQuery = searchQuery).pagination.totalCount
        return ceil(itemsCount / Constants.PAGE_SIZE * 1.0).toInt()
    }
}