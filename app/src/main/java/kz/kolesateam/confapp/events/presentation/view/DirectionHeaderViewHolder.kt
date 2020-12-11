package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.presentation.BaseViewHolder
import kz.kolesateam.confapp.events.presentation.models.DirectionHeaderItem
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem

class DirectionHeaderViewHolder(itemView: View) : BaseViewHolder<UpcomingEventListItem>(itemView) {

    private val directionTitleTextView = itemView.findViewById<TextView>(R.id.direction_activity_title)

    override fun onBind(data: UpcomingEventListItem) {
        val directionTitle: String = (data as? DirectionHeaderItem)?.directionTitle ?: return
        directionTitleTextView.text = directionTitle
    }
}