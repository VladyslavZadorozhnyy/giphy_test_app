package com.example.giphytestapp.domain.model

import com.example.cache.data.common.GifEntity
import com.example.giphytestapp.data.remote.dto.BaseGifDto

data class GifModel(
    val height: Int,
    val width: Int,
    val hash: String,
    val url: String = "",
    val filePath: String = ""
) {
    companion object {}
}

fun GifModel.Companion.fromGifDto(dto: BaseGifDto): GifModel {
    return GifModel(
        height = dto.height.toInt(),
        width = dto.width.toInt(),
        hash = dto.hash,
        url = dto.url
    )
}

fun GifModel.Companion.fromGifEntity(entity: GifEntity): GifModel {
    return GifModel(
        width = entity.width,
        height = entity.height,
        hash = entity.hash,
        filePath = entity.filePath,
    )
}

fun GifModel.toGifEntity(searchQuery: String): GifEntity {
    return GifEntity(
        width = this.width,
        height = this.height,
        hash = this.hash,
        filePath = this.filePath,
        searchQuery = searchQuery
    )
}