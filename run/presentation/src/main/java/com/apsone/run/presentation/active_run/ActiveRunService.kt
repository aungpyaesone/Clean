package com.apsone.run.presentation.active_run

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.apsone.core.presentation.designsystem.R
import com.apsone.core.presentation.ui.formatted
import com.apsone.run.domain.RunningTracker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class ActiveRunService : Service(){
    private val notificationManager by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getSystemService(NotificationManager::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        }
    }

    private val baseNotification by lazy {
        NotificationCompat.Builder(applicationContext,CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(getString(R.string.active_run))
    }

    private val runningTracker by inject<RunningTracker>()

    private var serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            ACTION_START -> {
                val activityClass = intent.getStringExtra(EXTRA_ACTIVITY_CLASS) ?: throw IllegalArgumentException("No activity class provided")
                start(Class.forName(activityClass))
            }
            ACTION_STOP -> {
                stop()
            }
        }
        return START_STICKY
    }

    private fun start(activityClass:Class<*>){
        if(!isServiceActive){
            isServiceActive = true
            startNotificationChannel()

            val activityIntent = Intent(applicationContext,activityClass).apply {
                data = "clean://active_run".toUri()
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }

            val pendingIntent = TaskStackBuilder.create(applicationContext).run{
                addNextIntentWithParentStack(activityIntent)
                getPendingIntent(0,PendingIntent.FLAG_IMMUTABLE)
            }
            val notification = baseNotification
                .setContentText("00:00:00")
                .setContentIntent(pendingIntent)
                .build()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                startForeground(1,notification)
            }
            updateNotification()
        }
    }

    private fun updateNotification(){
        runningTracker.elapsedTime.onEach {
            val notification = baseNotification
                .setContentText(it.formatted())
                .build()
            notificationManager.notify(1,notification)
        }.launchIn(serviceScope)
    }

    fun stop(){
        stopSelf()
        isServiceActive = false
        serviceScope.cancel()
        serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    }

    private fun startNotificationChannel() {
        if(Build.VERSION.SDK_INT >= 26){
            val channel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.active_run),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object{
        var isServiceActive = false
        const val CHANNEL_ID = "active_run_channel"
        const val ACTION_START = "start"
        const val ACTION_STOP = "stop"
        const val EXTRA_ACTIVITY_CLASS = "EXTRA_ACTIVITY_CLASS"

        fun createStartIntent(context: Context,activityClass: Class<*>):Intent{
            return Intent(context,ActiveRunService::class.java).apply {
                action = ACTION_START
                putExtra(EXTRA_ACTIVITY_CLASS,activityClass.name)
            }
        }

        fun createStopIntent(context: Context):Intent{
            return Intent(context,ActiveRunService::class.java).apply {
                action = ACTION_STOP
            }
        }
    }

}