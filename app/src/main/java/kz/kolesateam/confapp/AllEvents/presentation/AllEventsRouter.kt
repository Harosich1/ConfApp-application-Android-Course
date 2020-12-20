package kz.kolesateam.confapp.AllEvents.presentation

import android.content.Context
import android.content.Intent

const val BRANCH_ID = "branchId"
const val BRANCH_TITLE = "branchTitle"
const val PUSH_NOTIFICATION_MESSAGE = "push_message"

class AllEventsRouter {

    fun createIntent(
        context: Context,
        branchId: Int?,
        branchTitle: String?
    ) = Intent(context, AllEventsActivity::class.java).apply {
        putExtra(BRANCH_ID, branchId)
        putExtra(BRANCH_TITLE, branchTitle)
    }
}