package com.example.cache.data.common

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Constants.GIF_TABLE_NAME)
data class GifEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val width: Int,
    val height: Int,
    val hash: String,
    val filePath: String,
    val searchQuery: String,
    val cached: Boolean = true
)

fun GifEntity.generateFilepath(): String {
    return "${Constants.STORAGE_DIR_PATH}/${this.hash}.gif"
}