package kz.kolesateam.confapp.events.presentation

import kz.kolesateam.confapp.events.data.models.BranchApiData

interface UpcomingEventsHandler {
    fun setUpcomingEventsList(upcomingEventsItem: List<BranchApiData>)
}