package kz.kolesateam.confapp.common

import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.events.presentation.EventDetailsActivity

class EventDetailsRouter {

    fun createIntent(
        context: Context,
        branchId: Int?,
    ) = Intent(context, EventDetailsActivity::class.java).apply {
        putExtra(BRANCH_ID, branchId)
    }
}