package com.arthurabreu.allthingsandroid.app

import android.app.Application
import com.arthurabreu.allthingsandroid.core.di.appModule
import com.arthurabreu.allthingsandroid.core.di.networkModule
import com.arthurabreu.allthingsandroid.core.di.persistenceModule
import com.arthurabreu.allthingsandroid.core.di.viewModelsModule
import com.arthurabreu.allthingsandroid.feature.auth.di.authModule
import com.arthurabreu.allthingsandroid.feature.clouddb.di.cloudDbModule
import com.arthurabreu.allthingsandroid.feature.feed.di.feedModule
import com.arthurabreu.allthingsandroid.feature.maps.di.mapsModule
import com.arthurabreu.allthingsandroid.feature.notifications.di.notificationsModule
import com.arthurabreu.allthingsandroid.feature.player.di.playerModule
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
                viewModelsModule,
                networkModule,
                persistenceModule,
                feedModule,
                mapsModule,
                authModule,
                cloudDbModule,
                notificationsModule,
                playerModule,
            )
        }
    }
}