package com.example.hiltandbackgroundtask.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.hiltandbackgroundtask.broadcast.AlarmBroadcastReceiver
import java.time.ZoneId

class AndroidAlarmScheduler(private val context: Context) {
    companion object {
        val ALARM_MESSAGE = "alarm_message"
    }
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    fun schedule(alarmItem: AlarmItem){
        val intent = Intent(context, AlarmBroadcastReceiver::class.java).apply {
            putExtra(ALARM_MESSAGE, alarmItem.message)
        }
        Log.d("schedule ", "schedule: ")
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmItem.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            PendingIntent.getBroadcast(
                context,
                alarmItem.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
    fun cancel(alarmItem: AlarmItem){
        val intent = Intent(context, AlarmBroadcastReceiver::class.java).apply {
            putExtra(ALARM_MESSAGE, alarmItem.message)
        }
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarmItem.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

}