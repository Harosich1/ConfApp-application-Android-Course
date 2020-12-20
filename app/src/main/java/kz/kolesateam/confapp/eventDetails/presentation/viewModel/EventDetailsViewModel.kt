package kz.kolesateam.confapp.eventDetails.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.kolesateam.confapp.common.models.EventApiData
import kz.kolesateam.confapp.common.presentation.models.EventListItem
import kz.kolesateam.confapp.common.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.eventDetails.data.EventDetailsRepository
import kz.kolesateam.confapp.favourite_events.domain.FavouritesRepository
import kz.kolesateam.confapp.notifications.NotificationAlarmManager

class EventDetailsViewModel(
    private val eventDetailsRepository: EventDetailsRepository,
    private val upcomingFavouritesRepository: FavouritesRepository,
    private val notificationAlarmManager: NotificationAlarmManager
) : ViewModel() {

    private val eventDetailsLiveData: MutableLiveData<EventApiData> =
        MutableLiveData()

    fun getEventDetailsLiveData(): LiveData<EventApiData> = eventDetailsLiveData

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
        eventDetailsItem: EventApiData
    ) {
        eventDetailsLiveData.value = getEventDDetailsListItems(
            eventDetailsItem,
            upcomingFavouritesRepository.getAllFavouriteEvents()
        )
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

    private fun getEventDDetailsListItems(
        event: EventApiData,
        favouriteEventList: List<EventApiData>
    ): EventApiData {

        if (favouriteEventList.isNotEmpty()) {

            for (i in favouriteEventList.indices) {

                if (event.id == favouriteEventList[i].id) {
                    event.isFavourite = favouriteEventList[i].isFavourite
                    break
                }
            }
        }
        return event
    }

    private fun scheduleEvent(eventApiData: EventApiData) {
        notificationAlarmManager.createNotificationAlarm(eventApiData)
    }
}