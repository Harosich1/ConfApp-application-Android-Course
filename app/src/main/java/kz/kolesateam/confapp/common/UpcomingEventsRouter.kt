package kz.kolesateam.confapp.common

import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity

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