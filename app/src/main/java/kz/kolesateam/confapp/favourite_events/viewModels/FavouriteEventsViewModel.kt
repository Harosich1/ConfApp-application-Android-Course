package kz.kolesateam.confapp.favourite_events.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.kolesateam.confapp.common.models.EventApiData
import kz.kolesateam.confapp.common.presentation.models.FavouriteEventsItem
import kz.kolesateam.confapp.common.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.favourite_events.domain.FavouritesRepository
import kz.kolesateam.confapp.notifications.NotificationAlarmManager

class FavouriteEventsViewModel(
    private val eventsFavouritesRepository: FavouritesRepository,
    private val upcomingFavouritesRepository: FavouritesRepository,
    private val notificationAlarmManager: NotificationAlarmManager
) : ViewModel() {

    private val favouriteEventsLiveData: MutableLiveData<List<UpcomingEventListItem>> = MutableLiveData()

    fun getAllEventsLiveData(): LiveData<List<UpcomingEventListItem>> = favouriteEventsLiveData

    fun onLaunch() {
        favouriteEventsLiveData.value = getFavouriteEventsItem(eventsFavouritesRepository.getAllFavouriteEvents())
    }

    fun onFavouriteClick(
        eventApiData: EventApiData
    ) {
        when (eventApiData.isFavourite) {
            true -> upcomingFavouritesRepository.saveFavourite(eventApiData)

            else -> upcomingFavouritesRepository.removeFavouriteEvent(eventApiData.id)
        }
    }

    private fun getFavouriteEventsItem(
        eventList: List<EventApiData>
    ): List<UpcomingEventListItem> =
        eventList.map { eventApiData -> FavouriteEventsItem(data = eventApiData) }
}