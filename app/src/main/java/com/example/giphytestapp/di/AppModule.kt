package com.example.giphytestapp.di

import com.example.cache.data.database.GifCache
import com.example.cache.data.database.GifCacheImpl
import com.example.cache.data.database.room.GifRoomDatabase
import com.example.cache.data.storage.GifStorage
import com.example.cache.data.storage.GifStorageImpl
import com.example.giphytestapp.common.Constants
import com.example.giphytestapp.data.remote.GiphyAPI
import com.example.giphytestapp.data.repository.GifRepositoryImpl
import com.example.giphytestapp.domain.repository.GifRepository
import com.example.giphytestapp.domain.usecase.get.GetGifsUseCase
import com.example.giphytestapp.presentation.common.CollectionScreenViewModel
import com.example.giphytestapp.presentation.ui.fragments.CollectionFragment
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single { CollectionScreenViewModel() }

    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GiphyAPI::class.java)
    }

    single { GetGifsUseCase() }

    single<GifStorage> { GifStorageImpl() }

    single<GifCache> { GifCacheImpl(get(), get()) }

    single<GifRepository> { GifRepositoryImpl(get(), get()) }

    single { GifRoomDatabase.getDatabase(androidApplication()) }
}