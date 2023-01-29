package com.example.giphytestapp.common

import com.example.giphytestapp.domain.model.GifModel

object Constants {
    const val PAGE_SIZE = 25
    const val API_LANG = "en"
    const val BASE_URL = "https://api.giphy.com"
    const val API_KEY = "5VJevyzLt1qn7hza5VvhbWXxD1YSiZRV"

    val LOADING_ITEM = GifModel(-1, -1, "", "")
}