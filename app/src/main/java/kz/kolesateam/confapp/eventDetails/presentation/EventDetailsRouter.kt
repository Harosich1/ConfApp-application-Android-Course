package kz.kolesateam.confapp.eventDetails.presentation

import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.allEvents.presentation.BRANCH_ID

class EventDetailsRouter {
    fun createIntent(
        context: Context,
    ) = Intent(context, EventDetailsActivity::class.java)
}