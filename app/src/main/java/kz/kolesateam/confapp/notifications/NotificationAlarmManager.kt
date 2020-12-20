package kz.kolesateam.confapp.notifications

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*
import kz.kolesateam.confapp.common.models.EventApiData
import kz.kolesateam.confapp.utils.extensions.getEventFormattedDateTime
import org.threeten.bp.ZonedDateTime

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

        val formattedStartTime = ZonedDateTime.parse(eventApiData.startTime)

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.YEAR, formattedStartTime.year)
        calendar.set(Calendar.MONTH, formattedStartTime.monthValue)
        calendar.set(Calendar.DAY_OF_MONTH, formattedStartTime.dayOfMonth)
        calendar.set(Calendar.HOUR_OF_DAY, formattedStartTime.hour)
        calendar.set(Calendar.MINUTE, formattedStartTime.minute)
        calendar.set(Calendar.SECOND, formattedStartTime.second)

        alarmManager?.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis - 300000,
            pendingIntent
        )
    }
}