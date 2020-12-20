package kz.kolesateam.confapp.upcomingEvents.presentation

import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.AllEvents.presentation.PUSH_NOTIFICATION_MESSAGE
import kz.kolesateam.confapp.upcomingEvents.presentation.UpcomingEventsActivity

class UpcomingEventsRouter {

    private fun createIntent(
        context: Context,
    ) = Intent(context, UpcomingEventsActivity::class.java)

    fun createNotificationIntent(
        context: Context,
        messageFromPush: String
    ): Intent = createIntent(context).apply {
        putExtra(PUSH_NOTIFICATION_MESSAGE, messageFromPush)
    }
}