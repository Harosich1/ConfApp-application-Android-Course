package kz.kolesateam.confapp.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kz.kolesateam.confapp.R

object ConfAppNotificationManager {

    private lateinit var context: Context
    private var notificationId = 0

    fun init(applicationContext: Context){
        context = applicationContext
        createChannelIfNeeded()
    }

    fun sendNotification(
        title: String,
        content: String
    ) {
        val notification: Notification = createNotification(
            title = title,
            content = content
        )
        val notificationId = notificationId++
        NotificationManagerCompat.from(context).notify(
            notificationId,
            notification
        )
    }

    private fun createNotification(
        title: String,
        content: String
    ): Notification = NotificationCompat.Builder(context, getChannelId())
        .setSmallIcon(R.drawable.favorite_icon_filled)
        .setContentTitle(title)
        .setContentText(content)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()

    private fun getChannelId(): String = context.packageName + "push_channel"

    private fun createChannelIfNeeded() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val name = "confAppApplication"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(
            getChannelId(),
            name,
            importance
        )

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}