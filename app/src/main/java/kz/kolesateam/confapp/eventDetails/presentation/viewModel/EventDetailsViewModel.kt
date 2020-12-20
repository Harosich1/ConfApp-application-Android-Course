package kz.kolesateam.confapp.eventDetails.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.kolesateam.confapp.common.models.EventApiData
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
        eventDetailsLiveData.value = eventDetailsItem
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
}