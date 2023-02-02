package com.example.offline.di

import com.example.cache.data.repository.GifRoomImpl
import com.example.cache.data.room.GifRoomDatabase
import com.example.cache.domain.repository.GifRoomRepository
import com.example.cache.domain.usecase.GetSearchesUseCase
import com.example.cache.domain.usecase.RemoveGifUseCase
import com.example.offline.data.network.NetworkListener
import com.example.offline.domain.usecase.ObserveNetworkUseCase
import com.example.offline.domain.usecase.UploadSearchQueriesUseCase
import com.example.offline.presentation.viewmodels.NetworkViewModel
import com.example.offline.presentation.viewmodels.SearchesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val offlineModule = module {
    viewModel { NetworkViewModel() }

    viewModel { SearchesViewModel() }

    single { RemoveGifUseCase(get(), get()) }

    single { NetworkListener(androidApplication()) }

    single { GifRoomDatabase.getGifDao(androidApplication()) }

    single<GifRoomRepository> { GifRoomImpl(get()) }

    single { GetSearchesUseCase(get()) }

    single { ObserveNetworkUseCase(get()) }

    single { UploadSearchQueriesUseCase(get()) }
}