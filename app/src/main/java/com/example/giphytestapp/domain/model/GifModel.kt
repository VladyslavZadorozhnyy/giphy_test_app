package com.example.giphytestapp.domain.model

import com.example.cache.common.Constants
import com.example.cache.common.GifEntity
import com.example.giphytestapp.data.remote.dto.BaseGifDto

data class GifModel(
    val height: Int,
    val width: Int,
    val hash: String,
    val url: String = "",
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
        url = entity.generateUri()
    )
}

fun GifModel.toGifEntity(searchQuery: String): GifEntity {
    return GifEntity(
        width = this.width,
        height = this.height,
        hash = this.hash,
        searchQuery = searchQuery
    )
}

private fun GifEntity.generateUri(): String {
    return "${Constants.DIR_PATH}/${hash}.gif"
}