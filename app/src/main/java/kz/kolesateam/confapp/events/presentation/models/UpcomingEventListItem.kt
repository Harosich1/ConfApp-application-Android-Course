package kz.kolesateam.confapp.events.presentation.models

import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData

const val HEADER_TYPE: Int = 0
const val BRANCH_TYPE: Int = 1
const val EVENT_TYPE: Int = 2

sealed class UpcomingEventListItem(
        val type: Int
)

data class HeaderItem(
        val userName: String
) : UpcomingEventListItem(HEADER_TYPE)

data class BranchListItem (
        val data: BranchApiData
): UpcomingEventListItem(BRANCH_TYPE)

data class EventListItem (
        val data: EventApiData
): UpcomingEventListItem(EVENT_TYPE)