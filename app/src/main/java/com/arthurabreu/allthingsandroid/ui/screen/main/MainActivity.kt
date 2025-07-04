package com.arthurabreu.allthingsandroid.ui.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arthurabreu.allthingsandroid.core.navigation.NavigationGraph
import com.arthurabreu.allthingsandroid.ui.theme.AllThingsAndroidTheme
import com.arthurabreu.allthingsandroid.ui.viewmodel.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.ext.android.viewModel

/*
    * MainActivity is the entry point of the application.
 */
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()
    private val activityScope = CoroutineScope(Dispatchers.Main) // Use Main scope for UI operations


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AllThingsAndroidTheme {
                NavigationGraph(viewModel)
            }
        }

//        val workRequest = OneTimeWorkRequestBuilder<MyWorker>().build()
//        WorkManager.getInstance(this).enqueue(workRequest)
//
//        val serviceIntent = Intent(this@MainActivity, MyService::class.java)
//        startForegroundService(serviceIntent)
    }
}