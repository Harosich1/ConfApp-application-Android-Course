package kz.kolesateam.confapp.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

const val NOTIFICATION_TITLE = "Не пропустите доклад!"

class NotificationAlarmBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val content: String = intent?.getStringExtra(NOTIFICATION_CONTENT_KEY).orEmpty()

        ConfAppNotificationManager.sendNotification(
            title = NOTIFICATION_TITLE,
            content = content
        )
    }
}