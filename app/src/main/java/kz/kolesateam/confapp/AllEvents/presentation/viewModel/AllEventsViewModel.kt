package kz.kolesateam.confapp.AllEvents.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.kolesateam.confapp.AllEvents.data.AllEventsRepository
import kz.kolesateam.confapp.common.models.EventApiData
import kz.kolesateam.confapp.common.presentation.models.AllEventsHeaderItem
import kz.kolesateam.confapp.common.presentation.models.EventListItem
import kz.kolesateam.confapp.common.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.favourite_events.domain.FavouritesRepository
import kz.kolesateam.confapp.notifications.NotificationAlarmManager

class AllEventsViewModel(
    private val allEventsRepository: AllEventsRepository,
    private val upcomingFavouritesRepository: FavouritesRepository,
    private val notificationAlarmManager: NotificationAlarmManager
) : ViewModel() {

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
                setAllEventsList(allEventsItem, branchTitle)
            }
        )
    }

    private fun setAllEventsList(
        allEventsItem: List<EventApiData>,
        branchTitle: String
    ) {
        allEventsLiveData.value =
            listOf(getAllEventsHeaderItem(branchTitle)) + getEventListItems(allEventsItem)
    }

    fun onFavouriteClick(
        eventApiData: EventApiData
    ) {
        when (eventApiData.isFavourite) {
            true -> {
                upcomingFavouritesRepository.saveFavourite(eventApiData)
                scheduleEvent(eventApiData)
            }
            else -> upcomingFavouritesRepository.removeFavouriteEvent(eventApiData.id)
        }
    }

    private fun scheduleEvent(eventApiData: EventApiData) {
        notificationAlarmManager.createNotificationAlarm(
            content = eventApiData.title.orEmpty()
        )
    }

    private fun getEventListItems(
        eventList: List<EventApiData>
    ): List<UpcomingEventListItem> =
        eventList.map { eventApiData -> EventListItem(data = eventApiData) }

    private fun getAllEventsHeaderItem(branchTitle: String): AllEventsHeaderItem =
        AllEventsHeaderItem(
            allEventsTitle = branchTitle
        )
}