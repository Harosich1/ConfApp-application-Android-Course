package kz.kolesateam.confapp.common

import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.AllEvents.presentation.BRANCH_ID
import kz.kolesateam.confapp.eventDetails.presentation.EventDetailsActivity

class EventDetailsRouter {

    fun createIntent(
        context: Context,
        branchId: Int?,
    ) = Intent(context, EventDetailsActivity::class.java).apply {
        putExtra(BRANCH_ID, branchId)
    }
}