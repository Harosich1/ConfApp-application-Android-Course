package kz.kolesateam.confapp.common

import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.events.presentation.AllEventsActivity
import kz.kolesateam.confapp.utils.BRANCH_ID
import kz.kolesateam.confapp.utils.BRANCH_TITLE

class AllEventsRouter {

    fun createIntent(
        context: Context,
        branchId: Int?,
        branchTitle: String?,
    ) = Intent(context, AllEventsActivity::class.java).apply {
        putExtra(BRANCH_ID, branchId)
        putExtra(BRANCH_TITLE, branchTitle)
    }
}