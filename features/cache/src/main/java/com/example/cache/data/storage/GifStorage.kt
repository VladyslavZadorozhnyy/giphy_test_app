package com.example.cache.data.storage

import java.nio.ByteBuffer

interface GifStorage {
    suspend fun addToStorage(fileName: String, buffer: ByteBuffer?)

    suspend fun removeFromStorage(fileName: String)
}