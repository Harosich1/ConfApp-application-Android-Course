package kz.kolesateam.confapp.events.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.kolesateam.confapp.events.data.datasource.UserNameDataSource
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventsRepository
import kz.kolesateam.confapp.events.presentation.UpcomingEventsHandler
import kz.kolesateam.confapp.events.presentation.models.*
import kz.kolesateam.confapp.favourite_events.data.EventsFavouritesRepository
import kz.kolesateam.confapp.favourite_events.domain.FavouritesRepository
import kz.kolesateam.confapp.utils.HELLO_USER_FORMAT

class UpcomingEventsViewModel(
    private val upcomingEventsRepository: UpcomingEventsRepository,
    private val upcomingFavouritesRepository: FavouritesRepository,
    private val userNameDataSource: UserNameDataSource
) : ViewModel(), UpcomingEventsHandler {

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

    override fun setUpcomingEventsList(
        upcomingEventsItem: List<BranchApiData>
    ) {
        upcomingEventsLiveData.value = listOf(
            UpcomingHeaderItem(
                userName = HELLO_USER_FORMAT.format(getSavedUser())
            )
        ) + getBranchItems(upcomingEventsItem)
    }

    fun onFavouriteClick(
        eventApiData: EventApiData
    ) {
        when (eventApiData.isFavourite) {
            true -> upcomingFavouritesRepository.saveFavourite(eventApiData)
            else -> upcomingFavouritesRepository.removeFavouriteEvent(eventApiData.id)
        }
    }

    private fun getBranchItems(
        branchList: List<BranchApiData>
    ): List<UpcomingEventListItem> =
        branchList.map { branchApiData -> BranchListItem(data = branchApiData) }

    private fun getSavedUser(): String = userNameDataSource.getUserName() ?: ""
}