package com.example.giphytestapp

import android.app.Application
import com.example.giphytestapp.di.appModule
import com.example.offline.di.offlineModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GiphyTestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GiphyTestApp)
            modules(appModule, offlineModule)
        }
    }
}