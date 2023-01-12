package com.example.cache.data.storage

import android.os.Environment
import com.example.cache.data.common.Constants
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.io.File

class GifStorageImpl: GifStorage {
    init {
        createDirIfNotExists(Constants.STORAGE_DIR_PATH)
    }

    override suspend fun addToStorage(fileName: String, buffer: ByteBuffer?) {
        buffer?.let {
            val gifFile = File(Constants.STORAGE_DIR_PATH, fileName)
            val output = FileOutputStream(gifFile)
            val bytes = ByteArray(it.capacity())

            (it.duplicate().clear() as ByteBuffer).get(bytes)
            output.write(bytes, 0 ,bytes.size)
            output.close()
        }
    }

    override suspend fun removeFromStorage(fileName: String) {
        val gifFile = File(Constants.STORAGE_DIR_PATH, fileName)
        gifFile.delete()
    }

    private fun createDirIfNotExists(dirPath: String) {
        val dir = File(dirPath)

        if (!(dir.exists() && dir.isDirectory)) {
            dir.mkdirs()
        }
    }
}