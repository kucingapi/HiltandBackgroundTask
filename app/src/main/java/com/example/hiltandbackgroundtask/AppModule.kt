package com.example.hiltandbackgroundtask

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.example.hiltandbackgroundtask.broadcast.AirplaneBroadcastReceiver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
object AnalyticsModule {
    @Provides
    fun getAirplaneBroadcastReceiver() = AirplaneBroadcastReceiver()

    @ActivityScoped
    @Provides
    @Named("NotificationManager")
    fun getNotificationManager(
        @ApplicationContext context: Context
    ) = NotificationManagerCompat.from(context)
}
