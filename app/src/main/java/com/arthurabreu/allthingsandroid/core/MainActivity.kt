package com.arthurabreu.allthingsandroid.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arthurabreu.allthingsandroid.core.navigation.NavigationGraph
import com.arthurabreu.allthingsandroid.ui.theme.AllThingsAndroidTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

/*
    * MainActivity is the entry point of the application.
 */
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AllThingsAndroidTheme {
                NavigationGraph(viewModel)
            }
        }
    }
}