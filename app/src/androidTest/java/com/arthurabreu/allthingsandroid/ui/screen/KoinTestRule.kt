package com.arthurabreu.allthingsandroid.ui.screen

import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

class KoinTestRule(private val modules: List<Module>) : TestWatcher() {
    override fun starting(description: Description) {
        if (GlobalContext.getOrNull() != null) {
            stopKoin()
        }
        startKoin {
            modules(modules)
        }
    }

    override fun finished(description: Description) {
        stopKoin()
    }
}