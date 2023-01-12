package com.example.giphytestapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResponseDto(
    @SerializedName("data")
    val data: List<DataDto>,
    @SerializedName("pagination")
    val pagination: PaginationDto,
    @SerializedName("meta")
    val metaData: MetaDataDto
)