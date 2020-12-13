package kz.kolesateam.confapp.events.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.kolesateam.confapp.events.data.models.AllEventsRepository
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.AllEventsHandler
import kz.kolesateam.confapp.events.presentation.models.AllEventsHeaderItem
import kz.kolesateam.confapp.events.presentation.models.EventListItem
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem

class AllEventsViewModel(
    private val allEventsRepository: AllEventsRepository,
) : ViewModel(), AllEventsHandler {

    private val allEventsLiveData: MutableLiveData<List<UpcomingEventListItem>> =
        MutableLiveData()

    fun getAllEventsLiveData(): LiveData<List<UpcomingEventListItem>> = allEventsLiveData

    fun onLaunch(
        branchTitle: String,
        branchId: String
    ) {

        allEventsRepository.loadApiData(
            branchId = branchId,
            result = { allEventsItem ->
                setUpcomingEventsList(allEventsItem, branchTitle)
            }
        )
    }

    override fun setUpcomingEventsList(
        allEventsItem: List<EventApiData>,
        branchTitle: String
    ) {
        allEventsLiveData.value =
            listOf(getDirectionHeaderItem(branchTitle)) + getEventListItems(allEventsItem)
    }

    private fun getEventListItems(
        eventList: List<EventApiData>
    ): List<UpcomingEventListItem> =
        eventList.map { eventApiData -> EventListItem(data = eventApiData) }

    private fun getDirectionHeaderItem(branchTitle: String): AllEventsHeaderItem =
        AllEventsHeaderItem(
            allEventsTitle = branchTitle
        )
}