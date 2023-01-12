package com.example.cache.data.common

import android.os.Environment

object Constants {
    val STORAGE_DIR_PATH by lazy {
        "${Environment.getExternalStorageDirectory().absolutePath}/Download/giphy"
    }

    const val DATABASE_NAME: String = "gif_database"
    const val GIF_TABLE_NAME: String = "gif_table"
    const val DATABASE_VERSION: Int = 2
}