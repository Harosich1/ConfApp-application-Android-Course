package kz.kolesateam.confapp.AllEvents.presentation.view

import android.view.View
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.presentation.domain.BaseViewHolder
import kz.kolesateam.confapp.common.presentation.models.AllEventsHeaderItem
import kz.kolesateam.confapp.common.presentation.models.UpcomingEventListItem

class AllEventsHeaderViewHolder(itemView: View) : BaseViewHolder<UpcomingEventListItem>(itemView) {

    private val directionTitleTextView = itemView.findViewById<TextView>(R.id.all_events_activity_title)

    override fun onBind(data: UpcomingEventListItem) {
        val directionTitle: String = (data as? AllEventsHeaderItem)?.allEventsTitle ?: return
        directionTitleTextView.text = directionTitle
    }
}