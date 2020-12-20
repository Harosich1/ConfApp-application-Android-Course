package kz.kolesateam.confapp.eventDetails.presentation

import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.allEvents.presentation.BRANCH_ID
import kz.kolesateam.confapp.allEvents.presentation.PUSH_NOTIFICATION_MESSAGE

class EventDetailsRouter {

    fun createIntent(
        context: Context,
        branchId: Int?,
    ) = Intent(context, EventDetailsActivity::class.java).apply {
        putExtra(BRANCH_ID, branchId)
    }

    fun createNotificationIntent(
        context: Context,
        branchId: Int?,
        messageFromPush: String
    ): Intent = createIntent(context, branchId).apply {
        putExtra(PUSH_NOTIFICATION_MESSAGE, messageFromPush)
    }
}