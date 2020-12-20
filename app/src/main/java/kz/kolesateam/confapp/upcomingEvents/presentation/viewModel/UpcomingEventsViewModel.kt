package kz.kolesateam.confapp.upcomingEvents.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.kolesateam.confapp.common.datasource.UserNameDataSource
import kz.kolesateam.confapp.common.models.BranchApiData
import kz.kolesateam.confapp.common.models.EventApiData
import kz.kolesateam.confapp.upcomingEvents.data.UpcomingEventsRepository
import kz.kolesateam.confapp.common.presentation.models.*
import kz.kolesateam.confapp.favourite_events.domain.FavouritesRepository
import kz.kolesateam.confapp.notifications.NotificationAlarmManager

class UpcomingEventsViewModel(
    private val upcomingEventsRepository: UpcomingEventsRepository,
    private val upcomingFavouritesRepository: FavouritesRepository,
    private val userNameDataSource: UserNameDataSource,
    private val notificationAlarmManager: NotificationAlarmManager
) : ViewModel() {

    private val upcomingEventsLiveData: MutableLiveData<List<UpcomingEventListItem>> =
        MutableLiveData()

    fun getUpcomingEventsLiveData(): LiveData<List<UpcomingEventListItem>> = upcomingEventsLiveData

    fun onLaunch() {

        upcomingEventsRepository.loadApiData(
            result = { upcomingEventsItem ->
                setUpcomingEventsList(upcomingEventsItem)
            }
        )
    }

    private fun setUpcomingEventsList(
        upcomingEventsItem: List<BranchApiData>
    ) {
        upcomingEventsLiveData.value = listOf(
            UpcomingHeaderItem(
                userName = getSavedUser()
            )
        ) + getBranchItems(upcomingEventsItem, upcomingFavouritesRepository.getAllFavouriteEvents())
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
        notificationAlarmManager.createNotificationAlarm(eventApiData)
    }

    private fun getBranchItems(
        branchList: List<BranchApiData>,
        favouriteEventList: List<EventApiData>
    ): List<UpcomingEventListItem> {

        var eventList: List<EventApiData>

        if(favouriteEventList.isNotEmpty()) {

            for (i in branchList.indices) {

                eventList = branchList[i].events

                for (j in eventList.indices) {
                    for (z in favouriteEventList.indices) {

                        if (eventList[j].id == favouriteEventList[z].id) {
                            eventList[j].isFavourite = favouriteEventList[z].isFavourite
                            break
                        }
                    }
                }
            }
        }

        return branchList.map { branchApiData -> BranchListItem(data = branchApiData) }
    }

    private fun getSavedUser(): String = userNameDataSource.getUserName() ?: ""
}