package com.example.giphytestapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GifDtos(
    @SerializedName("original")
    val gifDtoOriginal: BaseGifDto,
    @SerializedName("downsized")
    val gifDtoDownsized: BaseGifDto,
    @SerializedName("fixed_height")
    val gifDtoFixedHeight: BaseGifDto,
)