package kz.kolesateam.confapp.notifications

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*
import kz.kolesateam.confapp.common.models.EventApiData

const val NOTIFICATION_CONTENT_KEY = "notification_title"

class NotificationAlarmManager(
    private val application: Application
) {

    fun createNotificationAlarm(
        eventApiData: EventApiData
    ) {
        val alarmManager: AlarmManager? = application.getSystemService(
            Context.ALARM_SERVICE
        ) as AlarmManager

        val pendingIntent: PendingIntent = Intent(
            application,
            NotificationAlarmBroadcastReceiver::class.java
        ).apply {
            putExtra(NOTIFICATION_CONTENT_KEY, eventApiData.title.orEmpty())
        }.let {
                PendingIntent.getBroadcast(application, 0, it ,PendingIntent.FLAG_ONE_SHOT)
        }

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR, 16)
        calendar.set(Calendar.MINUTE, 31)

        alarmManager?.setExact(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis()+ 1000,
            pendingIntent
        )
    }
}