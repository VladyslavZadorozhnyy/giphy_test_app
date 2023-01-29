package com.example.giphytestapp.data.remote

import com.example.giphytestapp.common.Constants
import com.example.giphytestapp.data.remote.dto.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyAPI {
    @GET("/v1/gifs/search")
    suspend fun getGifs(
        @Query("q") searchQuery: String,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("limit") limit: Int = Constants.PAGE_SIZE,
        @Query("offset") offset: Int = 0,
        @Query("rating") rating: String = "g",
        @Query("lang") language: String = Constants.API_LANG
    ): ResponseDto
}