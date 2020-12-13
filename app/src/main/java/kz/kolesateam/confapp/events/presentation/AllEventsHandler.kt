package kz.kolesateam.confapp.events.presentation

import kz.kolesateam.confapp.events.data.models.EventApiData

interface AllEventsHandler {
    fun setAllEventsList(allEventsItem: List<EventApiData>, branchTitle: String)
}