package com.arthurabreu.allthingsandroid.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.arthurabreu.allthingsandroid.ui.screen.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/*
 * MyService: An enhanced foreground service demonstrating various service capabilities.
 */
class MyService : Service() {

    private val myUseCase: MyUseCase by inject()
    private val serviceScope = CoroutineScope(Dispatchers.IO)
    private val CHANNEL_ID = "MyServiceChannel"
    private var wakeLock: PowerManager.WakeLock? = null

    /*
     * onCreate: Initializes the service, creates a notification channel, starts the foreground service, and acquires a WakeLock.
     */
    override fun onCreate() {
        super.onCreate()
        Log.d("MyService", "MyService: onCreate called")

        createNotificationChannel()
        val notification = createNotification()
        startForeground(1, notification)

        acquireWakeLock() // Acquire WakeLock to prevent device from sleeping

        Log.d("MyService", "MyService: Foreground service started")
    }

    /*
     * onStartCommand: Starts the long-running task in a coroutine, handles intent extras, and stops the service when finished.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyService", "MyService: onStartCommand called")

        // Example: Handle intent extras (e.g., data passed from the Activity)
        val data = intent?.getStringExtra("data")
        Log.d("MyService", "MyService: Received data: $data")

        serviceScope.launch {
            Log.d("MyService", "MyService: Starting long running task")
            myUseCase.performLongRunningTask().catch { e ->
                Log.e("MyService", "MyService: Error in long running task", e)
                stopSelf()
            }.collect {
                Log.d("MyService", it)
            }
            Log.d("MyService", "MyService: Long running task finished, stopping service")
            stopSelf()
        }
        return START_STICKY
    }

    /*
     * onBind: Returns null as this is not a bound service.
     */
    override fun onBind(intent: Intent?): IBinder? {
        Log.d("MyService", "MyService: onBind called")
        return null
    }

    /*
     * createNotificationChannel: Creates a notification channel for the foreground service.
     */
    private fun createNotificationChannel() {
        Log.d("MyService", "MyService: createNotificationChannel called")
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "My Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(serviceChannel)
    }

    /*
     * createNotification: Creates a notification for the foreground service with a tap action.
     */
    private fun createNotification(): android.app.Notification {
        val notificationIntent = Intent(this, MainActivity::class.java) // Replace MainActivity with your launcher activity
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE // required for newer android versions
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("My Service")
            .setContentText("Running in the background...")
            .setSmallIcon(android.R.drawable.ic_menu_upload)
            .setContentIntent(pendingIntent) // Add tap action
            .build()
    }

    /*
     * acquireWakeLock: Acquires a WakeLock to prevent the device from going to sleep.
     */
    private fun acquireWakeLock() {
        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyService:WakeLock")
        wakeLock?.acquire(10*60*1000L /*10 minutes*/)
        Log.d("MyService", "MyService: WakeLock acquired")
    }

    /*
     * releaseWakeLock: Releases the WakeLock.
     */
    private fun releaseWakeLock() {
        wakeLock?.release()
        wakeLock = null
        Log.d("MyService", "MyService: WakeLock released")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyService", "MyService: onDestroy called")
        releaseWakeLock() // Release WakeLock when service is destroyed
    }
}

// ... in your Activity or Fragment
//val serviceIntent = Intent(this, MyService::class.java)
//startForegroundService(serviceIntent)

/* Output
MyService: onCreate called
MyService: createNotificationChannel called
MyService: WakeLock acquired
MyService: Foreground service started
MyService: onStartCommand called
MyService: Received data: null
MyService: Starting long running task
MyRepositoryImpl: Starting long operation...
MyRepositoryImpl: Step 1: Processing data...
MyRepositoryImpl: Step 2: Performing calculations...
MyRepositoryImpl: Step 3: Saving results...
MyService: Long running task finished, stopping service
MyService: onDestroy called
MyService: WakeLock released
*/