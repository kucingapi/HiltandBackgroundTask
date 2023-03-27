package com.example.hiltandbackgroundtask.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AirplaneBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val state = intent?.getBooleanExtra("state", false) ?: return
        Toast.makeText(context, "the state of airplane is $state", Toast.LENGTH_SHORT).show()
    }
}