package kz.kolesateam.confapp.eventDetails.presentation

import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.AllEvents.presentation.BRANCH_ID

class EventDetailsRouter {

    fun createIntent(
        context: Context,
        branchId: Int?,
    ) = Intent(context, EventDetailsActivity::class.java).apply {
        putExtra(BRANCH_ID, branchId)
    }
}