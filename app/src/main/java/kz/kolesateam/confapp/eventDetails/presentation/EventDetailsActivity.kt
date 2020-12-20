package kz.kolesateam.confapp.eventDetails.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.AllEvents.presentation.BRANCH_ID
import kz.kolesateam.confapp.common.models.EventApiData
import kz.kolesateam.confapp.common.interactions.OnClick
import kz.kolesateam.confapp.eventDetails.presentation.view.EventDetailsViewHolder
import kz.kolesateam.confapp.eventDetails.presentation.viewModel.EventDetailsViewModel
import kz.kolesateam.confapp.favourite_events.domain.FavouriteEventActionObservable
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class EventDetailsActivity : AppCompatActivity(), OnClick {

    private val eventDetailsViewModel: EventDetailsViewModel by viewModel()
    private val favouriteEventActionObservable: FavouriteEventActionObservable by inject()

    private lateinit var eventDetailsViewHolder: EventDetailsViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        eventDetailsViewModel.onLaunch(intent.extras?.getInt(BRANCH_ID).toString())
        eventDetailsViewHolder = EventDetailsViewHolder(
            window.decorView.rootView,
            this
        )
        observeUpcomingEventsViewModel()
    }

    private fun observeUpcomingEventsViewModel() {
        eventDetailsViewModel.getEventDetailsLiveData().observe(this, ::showResult)
    }

    private fun showResult(eventApiData: EventApiData) {
        eventDetailsViewHolder.onBind(eventApiData)
    }

    override fun onFavouriteClick(eventApiData: EventApiData) {
        eventDetailsViewModel.onFavouriteClick(eventApiData)
    }
}