package kz.kolesateam.confapp.eventDetails.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.AllEvents.presentation.BRANCH_ID
import kz.kolesateam.confapp.common.interactions.FavoriteListener
import kz.kolesateam.confapp.common.models.EventApiData
import kz.kolesateam.confapp.eventDetails.presentation.view.EventDetailsViewHolder
import kz.kolesateam.confapp.eventDetails.presentation.viewModel.EventDetailsViewModel
import kz.kolesateam.confapp.favourite_events.domain.FavouriteEventActionObservable
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class EventDetailsActivity : AppCompatActivity(), FavoriteListener {

    private val eventDetailsViewModel: EventDetailsViewModel by viewModel()
    private val favouriteEventActionObservable: FavouriteEventActionObservable by inject()

    private lateinit var eventDetailsViewHolder: EventDetailsViewHolder

    private lateinit var branchArrowTransition: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        eventDetailsViewModel.onLaunch(intent.extras?.getInt(BRANCH_ID).toString())
        eventDetailsViewHolder = EventDetailsViewHolder(
            window.decorView.rootView,
            this
        )

        oBind()
        observeUpcomingEventsViewModel()
    }

    private fun oBind() {
        branchArrowTransition = findViewById(R.id.event_details_activity_navigation_button)

        branchArrowTransition.setOnClickListener {
            finishActivity()
        }
    }

    private fun finishActivity() {
        finish()
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