package com.example.cache.common

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Constants.GIF_TABLE_NAME)
data class GifEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val width: Int,
    val height: Int,
    val hash: String,
    val searchQuery: String,
    val cached: Boolean = true
)

fun GifEntity.generateFilename(): String {
    return "$hash.gif"
}