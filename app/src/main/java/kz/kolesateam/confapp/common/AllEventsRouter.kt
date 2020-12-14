package kz.kolesateam.confapp.common

import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.events.presentation.AllEventsActivity

const val BRANCH_ID = "branchId"
const val BRANCH_TITLE = "branchTitle"

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