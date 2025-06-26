package com.arthurabreu.allthingsandroid.app

import android.app.Application
import com.arthurabreu.allthingsandroid.core.di.appModule
import com.arthurabreu.allthingsandroid.core.di.networkModule
import com.arthurabreu.allthingsandroid.core.di.persistenceModule
import com.arthurabreu.allthingsandroid.core.di.screensModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class App : Application(){
    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                appModule,
                screensModule,
                networkModule,
                persistenceModule
            )
        }
    }
}