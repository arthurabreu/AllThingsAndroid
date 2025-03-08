package com.arthurabreu.allthingsandroid.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.arthurabreu.allthingsandroid.core.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/*
 * LocationService: A foreground service that continuously retrieves location updates.
 */
class LocationService : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: com.google.android.gms.location.LocationRequest
    private lateinit var locationCallback: LocationCallback
    private val serviceScope = CoroutineScope(Dispatchers.IO)
    private val CHANNEL_ID = "LocationServiceChannel"

    /*
     * onCreate: Initializes the location client, request, and callback.
     */
    override fun onCreate() {
        super.onCreate()
        Log.d("LocationService", "LocationService: onCreate called")

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        createLocationRequest()
        createLocationCallback()
        createNotificationChannel()

        val notification = createNotification()
        startForeground(2, notification)

        Log.d("LocationService", "LocationService: Foreground service started")
    }

    /*
     * onStartCommand: Starts requesting location updates.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("LocationService", "LocationService: onStartCommand called")
        requestLocationUpdates()
        return START_STICKY
    }

    /*
     * onBind: Returns null as this is not a bound service.
     */
    override fun onBind(intent: Intent?): IBinder? {
        Log.d("LocationService", "LocationService: onBind called")
        return null
    }

    /*
     * createLocationRequest: Creates a LocationRequest with desired parameters.
     */
    private fun createLocationRequest() {
        locationRequest = com.google.android.gms.location.LocationRequest.create().apply {
            interval = 10000 // Update location every 10 seconds
            fastestInterval = 5000 // Fastest interval
            priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    /*
     * createLocationCallback: Creates a LocationCallback to handle location updates.
     */
    private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    Log.d("LocationService", "Location updated: ${location.latitude}, ${location.longitude}")
                    // You can send this location data to your server or perform other actions
                }
            }
        }
    }

    /*
     * requestLocationUpdates: Requests location updates from the FusedLocationProviderClient.
     */
    private fun requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("LocationService", "Location permissions not granted")
            stopSelf()
            return
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    /*
     * removeLocationUpdates: Removes location updates.
     */
    private fun removeLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    /*
     * createNotificationChannel: Creates a notification channel for the foreground service.
     */
    private fun createNotificationChannel() {
        Log.d("LocationService", "LocationService: createNotificationChannel called")
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Location Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(serviceChannel)
    }

    /*
     * createNotification: Creates a notification for the foreground service.
     */
    private fun createNotification(): android.app.Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Location Service")
            .setContentText("Getting location updates...")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .setContentIntent(pendingIntent)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LocationService", "LocationService: onDestroy called")
        removeLocationUpdates()
    }
}