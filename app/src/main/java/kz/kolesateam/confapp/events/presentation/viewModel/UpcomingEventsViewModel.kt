package kz.kolesateam.confapp.events.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.kolesateam.confapp.events.data.datasource.UserNameDataSource
import kz.kolesateam.confapp.events.data.models.UpcomingEventsRepository
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem

class UpcomingEventsViewModel(
        private val upcomingEventsRepository: UpcomingEventsRepository,
        private val userNameDataSource: UserNameDataSource
) : ViewModel() {

    private val upcomingEventsLiveData: MutableLiveData<List<UpcomingEventListItem>> = MutableLiveData()

    fun onLaunch(
            branchTitle: String,
            branchId: String,
            result: (List<UpcomingEventListItem>) -> Unit
    ) {

    }

}