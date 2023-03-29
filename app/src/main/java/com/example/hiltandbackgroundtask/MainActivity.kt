package com.example.hiltandbackgroundtask

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.hiltandbackgroundtask.alarm.AlarmItem
import com.example.hiltandbackgroundtask.alarm.AndroidAlarmScheduler
import com.example.hiltandbackgroundtask.broadcast.AirplaneBroadcastReceiver
import com.example.hiltandbackgroundtask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val CHANNEL_ID = "random"
    val CHANNEL_NAME = "random_notification"
    val NOTIFICATION_ID = 0

    @Inject
    lateinit var receiver: AirplaneBroadcastReceiver

    @Inject
    @Named("NotificationManager")
    lateinit var notificationManager: NotificationManagerCompat

    lateinit var scheduler: AndroidAlarmScheduler

    lateinit var notificationBuilder: Notification
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        scheduler = AndroidAlarmScheduler(this)
        registerAirplaneReceiver()
        createNotification()
        setBindingEvent()
    }


    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }

    private fun registerAirplaneReceiver(){
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(receiver, it)
        }
    }

    private fun createNotification() {
        createNotificationChannel()
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_star_24)
            .setContentTitle("test")
            .setContentText("this is notification")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }
        val descriptionText = "this notification is random notification"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    @SuppressLint("MissingPermission")
    private fun setBindingEvent() {
        binding.buttonNotification.setOnClickListener {
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder)
            scheduler.schedule(AlarmItem("test", LocalDateTime.now()))
        }
    }
}
