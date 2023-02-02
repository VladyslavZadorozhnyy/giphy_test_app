package com.example.giphytestapp.di

import com.bumptech.glide.Glide
import com.example.cache.domain.repository.GifStorageRepository
import com.example.cache.data.repository.GifStorageImpl
import com.example.cache.domain.usecase.CacheGifUseCase
import com.example.giphytestapp.common.Constants
import com.example.giphytestapp.data.remote.GiphyAPI
import com.example.giphytestapp.data.repository.GifRepositoryImpl
import com.example.giphytestapp.domain.repository.GifRepository
import com.example.giphytestapp.domain.usecase.GetGifsUseCase
import com.example.giphytestapp.domain.usecase.UploadGifsUseCase
import com.example.giphytestapp.presentation.viewmodels.AppViewModel
import com.example.giphytestapp.presentation.viewmodels.CollectionViewModel
import com.example.giphytestapp.presentation.viewmodels.NavigationViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GiphyAPI::class.java)
    }

    viewModel { AppViewModel(get(), get(), get()) }

    viewModel { NavigationViewModel() }

    viewModel { CollectionViewModel() }

    single { GetGifsUseCase(get(), get()) }

    single { UploadGifsUseCase(get()) }

    single { CacheGifUseCase(get(), get()) }

    single { Glide.with(androidApplication()) }

    single<GifStorageRepository> { GifStorageImpl() }

    single<GifRepository> { GifRepositoryImpl(get(), get()) }
}