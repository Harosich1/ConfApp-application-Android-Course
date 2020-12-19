package kz.kolesateam.confapp.events.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.BRANCH_ID
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.listeners.OnClick
import kz.kolesateam.confapp.events.presentation.listeners.OnEventClick
import kz.kolesateam.confapp.events.presentation.models.EventDetailsItem
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.presentation.view.EventDetailsViewHolder
import kz.kolesateam.confapp.events.presentation.viewModel.AllEventsViewModel
import kz.kolesateam.confapp.events.presentation.viewModel.EventDetailsViewModel
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