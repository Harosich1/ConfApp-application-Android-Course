package kz.kolesateam.confapp.favourite_events.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.kolesateam.confapp.common.models.EventApiData
import kz.kolesateam.confapp.common.presentation.models.FavouriteEventsItem
import kz.kolesateam.confapp.common.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.favourite_events.domain.FavouritesRepository

class FavouriteEventsViewModel(
    private val eventsFavouritesRepository: FavouritesRepository
) : ViewModel() {

    private val favouriteEventsLiveData: MutableLiveData<List<UpcomingEventListItem>> = MutableLiveData()

    fun getAllEventsLiveData(): LiveData<List<UpcomingEventListItem>> = favouriteEventsLiveData

    fun onLaunch() {
        favouriteEventsLiveData.value = getFavouriteEventsItem(eventsFavouritesRepository.getAllFavouriteEvents())
    }

    private fun getFavouriteEventsItem(
        eventList: List<EventApiData>
    ): List<UpcomingEventListItem> =
        eventList.map { eventApiData -> FavouriteEventsItem(data = eventApiData) }
}