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
import kz.kolesateam.confapp.utils.DATE_OF_EVENT
import kz.kolesateam.confapp.utils.extensions.getEventFormattedDateTime
import org.threeten.bp.ZonedDateTime

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

    fun onFavouriteClick(
        eventApiData: EventApiData
    ) {
        when (eventApiData.isFavourite) {
            true -> {
                upcomingFavouritesRepository.saveFavourite(eventApiData)
                scheduleEvent(eventApiData, true)
            }
            else -> {
                upcomingFavouritesRepository.removeFavouriteEvent(eventApiData.id)
                scheduleEvent(eventApiData, false)
            }
        }
    }

    private fun setEventDetailsList(
        eventDetailsItem: EventApiData
    ) {
        eventDetailsLiveData.value = getEventDDetailsListItems(
            eventDetailsItem,
            upcomingFavouritesRepository.getAllFavouriteEvents()
        )
    }

    private fun getEventDDetailsListItems(
        event: EventApiData,
        favouriteEventList: List<EventApiData>
    ): EventApiData {

        val formattedStartTime = ZonedDateTime.parse(event.startTime).getEventFormattedDateTime()
        val formattedEndTime = ZonedDateTime.parse(event.endTime).getEventFormattedDateTime()

        event.dateOfEvent = DATE_OF_EVENT.format(
            formattedStartTime,
            formattedEndTime,
            event.place
        )

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

    private fun scheduleEvent(eventApiData: EventApiData, isNotCancelled: Boolean) {
        notificationAlarmManager.createNotificationAlarm(eventApiData, isNotCancelled)
    }
}