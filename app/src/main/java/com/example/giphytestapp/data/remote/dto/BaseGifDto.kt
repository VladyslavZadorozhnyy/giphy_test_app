package com.example.giphytestapp.data.remote.dto

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