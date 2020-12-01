package kz.kolesateam.confapp.events.data.models

const val HEADER_TYPE: Int = 0
const val EVENT_TYPE: Int = 1

sealed class UpcomingEventListItem(
        val type: Int
)

data class HeaderItem(
        val userName: String
) : UpcomingEventListItem(HEADER_TYPE)

data class BranchListItem (
        val data: BranchApiData
): UpcomingEventListItem(EVENT_TYPE)