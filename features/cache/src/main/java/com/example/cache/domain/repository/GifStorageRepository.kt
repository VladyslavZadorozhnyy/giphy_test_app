package com.example.cache.domain.repository

import java.nio.ByteBuffer

interface GifStorageRepository {
    suspend fun addToStorage(fileName: String, buffer: ByteBuffer?)

    suspend fun removeFromStorage(fileName: String)
}