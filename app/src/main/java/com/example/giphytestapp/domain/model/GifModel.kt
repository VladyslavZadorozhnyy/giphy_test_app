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
    companion object {
        fun fromGifDto(dto: BaseGifDto) = GifModel(
            dto.height.toInt(),
            dto.width.toInt(),
            dto.hash,
            dto.url
        )

        fun fromGifEntity(entity: GifEntity) = GifModel(
            entity.width,
            entity.height,
            entity.hash,
            entity.generateUri()
        )
    }
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