package com.example.cache.common

import android.os.Environment.getExternalStorageDirectory

object Constants {
    val DIR_PATH by lazy { "${getExternalStorageDirectory().absolutePath}/Download/.giphy" }
    const val NO_MEDIA_FILENAME = ".nomedia"

    const val DATABASE_NAME: String = "gif_database"
    const val GIF_TABLE_NAME: String = "gif_table"
    const val DATABASE_VERSION: Int = 2
}