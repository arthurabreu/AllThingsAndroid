package com.arthurabreu.allthingsandroid.core

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.arthurabreu.allthingsandroid.core.navigation.NavigationGraph
import com.arthurabreu.allthingsandroid.services.LocationService
import com.arthurabreu.allthingsandroid.services.MyService
import com.arthurabreu.allthingsandroid.services.MyWorker
import com.arthurabreu.allthingsandroid.ui.theme.AllThingsAndroidTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

/*
    * MainActivity is the entry point of the application.
 */
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        // Normal way to handle permission denial
//        if (isGranted) {
//            startLocationService()
//        } else {
//            // Handle permission denial
//        }
        // To keep asking for permission every time the app is opened
        startLocationService()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AllThingsAndroidTheme {
                NavigationGraph(viewModel)
            }
        }

        val workRequest = OneTimeWorkRequestBuilder<MyWorker>().build()
        WorkManager.getInstance(this).enqueue(workRequest)

        val serviceIntent = Intent(this@MainActivity, MyService::class.java)
        startForegroundService(serviceIntent)

        checkLocationPermissions()
    }

    // Normal asking permission way
//    private fun checkLocationPermissions() {
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            startLocationService()
//        } else {
//            locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//        }
//    }

    // To launch the permission request every time the app is opened
    private fun checkLocationPermissions() {
        locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun startLocationService() {
        val serviceIntent = Intent(this, LocationService::class.java)
        startForegroundService(serviceIntent)
    }
}