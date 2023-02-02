package com.example.cache.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cache.common.Constants
import com.example.cache.common.GifEntity


@Database(entities = [GifEntity::class], version = Constants.DATABASE_VERSION)
abstract class GifRoomDatabase: RoomDatabase() {
    abstract fun gifDao(): GifDao

    companion object {
        private var instance: GifRoomDatabase? = null

        fun getGifDao(context: Context): GifDao {
            synchronized(GifRoomDatabase::class.java) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GifRoomDatabase::class.java,
                        Constants.DATABASE_NAME
                    ).build()
                }
            }
            return instance!!.gifDao()
        }
    }
}