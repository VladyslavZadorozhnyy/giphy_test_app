package com.example.giphytestapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DataDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("import_datetime")
    val importDatetime: String,
    @SerializedName("images")
    val images: GifDtos
)