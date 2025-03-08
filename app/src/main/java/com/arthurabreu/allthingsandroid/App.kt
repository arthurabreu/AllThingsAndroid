package com.arthurabreu.allthingsandroid

import android.app.Application
import com.arthurabreu.allthingsandroid.core.di.modules.appModule
import com.arthurabreu.allthingsandroid.core.di.modules.networkModule
import com.arthurabreu.allthingsandroid.core.di.modules.persistenceModule
import com.arthurabreu.allthingsandroid.core.di.modules.screensModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin{
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