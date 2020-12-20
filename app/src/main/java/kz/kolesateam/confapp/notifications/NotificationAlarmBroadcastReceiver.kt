package kz.kolesateam.confapp.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.AllEvents.presentation.BRANCH_ID

const val NOTIFICATION_TITLE = "Не пропустите доклад!"

class NotificationAlarmBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val content: String = intent?.getStringExtra(NOTIFICATION_CONTENT_KEY).orEmpty()
        val branchId: Int? = intent?.getIntExtra(BRANCH_ID, 0)

        ConfAppNotificationManager.sendNotification(
            title = NOTIFICATION_TITLE,
            branchId,
            content = content
        )
    }
}