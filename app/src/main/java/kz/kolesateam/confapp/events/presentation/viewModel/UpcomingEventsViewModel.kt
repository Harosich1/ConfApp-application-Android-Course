package kz.kolesateam.confapp.events.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.kolesateam.confapp.events.data.datasource.UserNameDataSource
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventsRepository
import kz.kolesateam.confapp.events.presentation.UpcomingEventsHandler
import kz.kolesateam.confapp.events.presentation.models.*
import kz.kolesateam.confapp.utils.HELLO_USER_FORMAT

class UpcomingEventsViewModel(
    private val upcomingEventsRepository: UpcomingEventsRepository,
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

    private fun getBranchItems(
        branchList: List<BranchApiData>
    ): List<UpcomingEventListItem> =
        branchList.map { branchApiData -> BranchListItem(data = branchApiData) }

    override fun setUpcomingEventsList(
        upcomingEventsItem: List<BranchApiData>
    ) {
        upcomingEventsLiveData.value = listOf(
            UpcomingHeaderItem(
                userName = HELLO_USER_FORMAT.format(getSavedUser())
            )
        ) + getBranchItems(upcomingEventsItem)
    }

    private fun getSavedUser(): String = userNameDataSource.getUserName() ?: ""
}