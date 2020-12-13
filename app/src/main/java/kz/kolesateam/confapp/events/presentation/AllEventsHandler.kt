package kz.kolesateam.confapp.events.presentation

import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem

interface AllEventsHandler {
    fun setUpcomingEventsList(allEventsItem: List<EventApiData>, branchTitle: String)
}