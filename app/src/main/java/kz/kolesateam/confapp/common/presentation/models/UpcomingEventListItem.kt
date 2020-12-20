package kz.kolesateam.confapp.common.presentation.models

import kz.kolesateam.confapp.common.models.BranchApiData
import kz.kolesateam.confapp.common.models.EventApiData

const val UPCOMING_HEADER_TYPE: Int = 0
const val BRANCH_TYPE: Int = 1
const val EVENT_TYPE: Int = 2
const val ALL_EVENTS_HEADER_TYPE: Int = 3
const val FAVOURITE_EVENTS_TYPE: Int = 4
const val EVENT_DETAILS_TYPE: Int = 5

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

data class FavouriteEventsItem (
        val data: EventApiData
): UpcomingEventListItem(FAVOURITE_EVENTS_TYPE)

data class EventDetailsItem (
        val data: EventApiData
): UpcomingEventListItem(EVENT_DETAILS_TYPE)