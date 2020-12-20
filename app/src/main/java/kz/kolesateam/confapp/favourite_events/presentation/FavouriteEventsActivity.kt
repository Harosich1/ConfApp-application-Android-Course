package kz.kolesateam.confapp.favourite_events.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.models.EventApiData
import kz.kolesateam.confapp.common.interactions.BranchListener
import kz.kolesateam.confapp.common.interactions.EventListener
import kz.kolesateam.confapp.common.interactions.FavoriteListener
import kz.kolesateam.confapp.common.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.common.presentation.view.BranchAdapter
import kz.kolesateam.confapp.eventDetails.presentation.EventDetailsRouter
import kz.kolesateam.confapp.favourite_events.viewModels.FavouriteEventsViewModel
import kz.kolesateam.confapp.favourite_events.domain.FavouriteEventActionObservable
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class FavouriteEventsActivity : AppCompatActivity(), BranchListener, FavoriteListener, EventListener {

    private val favouriteEventsViewModel: FavouriteEventsViewModel by viewModel()
    private val favouriteEventActionObservable: FavouriteEventActionObservable by inject()
    private val eventDetailsRouter: EventDetailsRouter = EventDetailsRouter()

    private lateinit var recyclerView: RecyclerView
    private lateinit var branchAdapter: BranchAdapter
    private lateinit var onMainPageButton: Button
    private lateinit var emptyFavouritesTextView: TextView
    private lateinit var emptyFavouritesImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_events)

        bindViews()
        favouriteEventsViewModel.onLaunch()
        observeUpcomingEventsViewModel()
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.activity_favourite_events_recycler)
        onMainPageButton = findViewById(R.id.favourite_events_activity_button_on_main_page)
        emptyFavouritesTextView = findViewById(R.id.favourite_events_screen_empty_favourites_text_view)
        emptyFavouritesImageView = findViewById(R.id.icon_favourite_events_space_astronaut)

        branchAdapter = BranchAdapter(
            eventBranchListener = this,
            eventFavoriteListener = this,
            favouriteEventActionObservable = favouriteEventActionObservable,
            eventListener = this
        )

        recyclerView.adapter = branchAdapter
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        onMainPageButton.setOnClickListener {
            navigateToUpcomingEventsActivity()
        }
    }

    private fun observeUpcomingEventsViewModel() {
        favouriteEventsViewModel.getAllEventsLiveData().observe(this, ::showResult)
    }

    private fun showResult(upcomingEventListItem: List<UpcomingEventListItem>) {
        branchAdapter.setList(
            upcomingEventListItem.apply {
                if (this.isEmpty()) {
                    emptyFavouritesTextView.visibility = View.VISIBLE
                    emptyFavouritesImageView.visibility = View.VISIBLE
                }
            }
        )
    }

    private fun navigateToUpcomingEventsActivity() {
        finish()
    }

    override fun onBranchClicked(branchId: Int?, title: String?) {
    }

    override fun onFavouriteClick(eventApiData: EventApiData) {
        favouriteEventsViewModel.onFavouriteClick(eventApiData)
        updateFavouriteAdapter()
    }

    private fun updateFavouriteAdapter() {
        favouriteEventsViewModel.onLaunch()
    }

    override fun onEventClick(branchId: Int?) {
        val eventDetailsActivity = eventDetailsRouter.createIntent(context = this, branchId)
        startActivity(eventDetailsActivity)
    }
}