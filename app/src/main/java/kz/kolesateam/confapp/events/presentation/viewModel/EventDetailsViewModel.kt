package kz.kolesateam.confapp.events.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.EventDetailsRepository
import kz.kolesateam.confapp.events.presentation.models.EventDetailsItem
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.favourite_events.domain.FavouritesRepository
import kz.kolesateam.confapp.notifications.NotificationAlarmManager

class EventDetailsViewModel(
    private val eventDetailsRepository: EventDetailsRepository,
    private val upcomingFavouritesRepository: FavouritesRepository,
    private val notificationAlarmManager: NotificationAlarmManager
) : ViewModel() {

    private val eventDetailsLiveData: MutableLiveData<List<UpcomingEventListItem>> =
        MutableLiveData()

    fun getEventDetailsLiveData(): LiveData<List<UpcomingEventListItem>> = eventDetailsLiveData

    fun onLaunch(
        branchId: String
    ) {

        eventDetailsRepository.loadApiData(
            branchId = branchId,
            result = { eventDetailsItem ->
                setEventDetailsList(eventDetailsItem)
            }
        )
    }

    private fun setEventDetailsList(
        eventDetailsItem: List<EventApiData>,
    ) {
        eventDetailsLiveData.value = getEventDetailsListItems(eventDetailsItem)
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

    private fun getEventDetailsListItems(
        eventList: List<EventApiData>
    ): List<UpcomingEventListItem> =
        eventList.map { eventApiData -> EventDetailsItem(data = eventApiData) }
}