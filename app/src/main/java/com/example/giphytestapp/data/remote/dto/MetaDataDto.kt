package com.example.giphytestapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MetaDataDto(
    @SerializedName("status")
    val status: String,
    @SerializedName("msg")
    val message: String,
    @SerializedName("response_id")
    val responseId: String,
)