package com.example.giphytestapp.data.remote.dto

import com.example.cache.data.common.GifEntity
import com.example.giphytestapp.domain.model.GifModel
import com.google.gson.annotations.SerializedName

data class BaseGifDto(
    @SerializedName("height")
    val height: String,
    @SerializedName("width")
    val width: String,
    @SerializedName("size")
    val size: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("hash")
    val hash: String
)

// TODO: remove this method if it's not used
//fun BaseGifDto.toGifEntity(searchQuery: String): GifEntity {
//    return GifEntity(
//        width = this.width.toIntOrNull() ?: 0,
//        height = this.height.toIntOrNull() ?: 0,
//        hash = this.hash,
//        filePath = "some_constants_path/${this.hash}.gif",
//        searchQuery = searchQuery,
//        cached = true
//    )
//}