package com.example.cache.data.repository

import com.example.cache.common.Constants
import com.example.cache.domain.repository.GifStorageRepository
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.io.File

class GifStorageImpl: GifStorageRepository {
    init {
        createDirIfNotExists(Constants.DIR_PATH)
        createNoMediaFile()
    }

    override suspend fun addToStorage(fileName: String, buffer: ByteBuffer) {
        val gifFile = File(Constants.DIR_PATH, fileName)
        val output = FileOutputStream(gifFile)
        val bytes = ByteArray(buffer.capacity())

        (buffer.duplicate().clear() as ByteBuffer).get(bytes)
        output.write(bytes, 0 ,bytes.size)
        output.close()
    }

    override suspend fun removeFromStorage(fileName: String) {
        val gifFile = File(Constants.DIR_PATH, fileName)
        gifFile.delete()
    }

    private fun createDirIfNotExists(dirPath: String) {
        val dir = File(dirPath)

        if (!(dir.exists() && dir.isDirectory)) {
            dir.mkdirs()
        }
    }

    private fun createNoMediaFile() {
        val noMediaFile = File(Constants.DIR_PATH, Constants.NO_MEDIA_FILENAME)

        if (!noMediaFile.exists()) {
            noMediaFile.createNewFile()
        }
    }
}