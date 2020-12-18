package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.presentation.BaseViewHolder
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.listeners.OnClick
import kz.kolesateam.confapp.events.presentation.listeners.OnEventClick
import kz.kolesateam.confapp.events.presentation.models.EventDetailsItem
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem

class EventDetailsViewHolder(
    itemView: View,
) {

    private val event: View = itemView.findViewById(R.id.event_details)

    private val eventDescription: TextView = event.findViewById(R.id.description_of_event)

    fun onBind(eventApiData: EventApiData) {
        val eventApiData: EventApiData = (eventApiData as? EventDetailsItem)?.data ?: return

        eventDescription.text = eventApiData.description
    }
}