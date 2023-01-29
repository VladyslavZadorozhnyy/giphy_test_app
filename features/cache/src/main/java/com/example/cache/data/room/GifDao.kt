package com.example.cache.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cache.common.Constants
import com.example.cache.common.GifEntity

@Dao
interface GifDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToCache(entity: GifEntity)

    @Query("UPDATE ${Constants.GIF_TABLE_NAME} SET cached=0 WHERE hash=:hash")
    fun removeFromCache(hash: String)

    @Query("DELETE FROM ${Constants.GIF_TABLE_NAME}")
    fun clearCache()

    @Query("SELECT * FROM ${Constants.GIF_TABLE_NAME} WHERE hash=:hash LIMIT 1")
    fun getByHash(hash: String): GifEntity?

    @Query("SELECT * FROM ${Constants.GIF_TABLE_NAME} WHERE searchQuery=:searchQuery AND cached=1")
    fun getBySearchQuery(searchQuery: String): List<GifEntity>

    @Query("SELECT * FROM ${Constants.GIF_TABLE_NAME}")
    fun getAll(): List<GifEntity>

    @Query("SELECT DISTINCT searchQuery FROM ${Constants.GIF_TABLE_NAME}")
    fun getCachedQueries(): List<String>
}