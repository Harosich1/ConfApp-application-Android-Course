package kz.kolesateam.confapp.events.presentation.models

import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData

const val UPCOMING_HEADER_TYPE: Int = 0
const val BRANCH_TYPE: Int = 1
const val EVENT_TYPE: Int = 2
const val ALL_EVENTS_HEADER_TYPE: Int = 3

sealed class UpcomingEventListItem(
        val type: Int
)

data class UpcomingHeaderItem(
        val userName: String
) : UpcomingEventListItem(UPCOMING_HEADER_TYPE)

data class AllEventsHeaderItem(
        val allEventsTitle: String
) : UpcomingEventListItem(ALL_EVENTS_HEADER_TYPE)

data class BranchListItem (
        val data: BranchApiData
): UpcomingEventListItem(BRANCH_TYPE)

data class EventListItem (
        val data: EventApiData
): UpcomingEventListItem(EVENT_TYPE)