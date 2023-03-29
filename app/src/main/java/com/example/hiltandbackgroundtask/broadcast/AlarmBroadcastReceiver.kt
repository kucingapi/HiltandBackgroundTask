package com.example.hiltandbackgroundtask.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.hiltandbackgroundtask.alarm.AndroidAlarmScheduler

class AlarmBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra(AndroidAlarmScheduler.ALARM_MESSAGE) ?: return
        Log.d("message", "onReceive: $message")
    }

}