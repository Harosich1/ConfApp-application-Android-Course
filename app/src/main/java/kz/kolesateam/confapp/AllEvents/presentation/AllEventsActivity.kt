package kz.kolesateam.confapp.AllEvents.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.AllEvents.presentation.viewModel.AllEventsViewModel
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.models.EventApiData
import kz.kolesateam.confapp.common.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.common.presentation.view.BranchAdapter
import kz.kolesateam.confapp.common.interaction.BranchListener
import kz.kolesateam.confapp.common.interaction.EventListener
import kz.kolesateam.confapp.common.interactions.FavoriteListener
import kz.kolesateam.confapp.eventDetails.presentation.EventDetailsRouter
import kz.kolesateam.confapp.favourite_events.domain.FavouriteEventActionObservable
import kz.kolesateam.confapp.favourite_events.presentation.FavouriteEventsActivity
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class AllEventsActivity : AppCompatActivity(), BranchListener, FavoriteListener, EventListener{

    private val allEventsViewModel: AllEventsViewModel by viewModel()
    private val favouriteEventActionObservable: FavouriteEventActionObservable by inject()
    private val eventDetailsRouter: EventDetailsRouter = EventDetailsRouter()

    private lateinit var recyclerView: RecyclerView
    private lateinit var branchAdapter: BranchAdapter
    private lateinit var inYourFavouriteButton: Button
    private lateinit var arrowActionBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_events_layout)

        val branchId: Int? = intent.extras?.getInt(BRANCH_ID)
        val branchTitle: String? = intent.extras?.getString(BRANCH_TITLE)

        bindViews()
        observeUpcomingEventsViewModel()
        allEventsViewModel.onLaunch(branchTitle = branchTitle!!, branchId = branchId!!.toString())
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.activity_all_events_recycler)
        arrowActionBack = findViewById(R.id.all_events_activity_navigation_button)
        inYourFavouriteButton = findViewById(R.id.all_events_activity_button_in_favourite)

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
        recyclerView.setPadding(60, 0, 60, 240)

        arrowActionBack.setOnClickListener {
            navigateToUpcomingEventsActivity()
        }
        inYourFavouriteButton.setOnClickListener {
            navigateToFavouriteEventsActivity()
        }
    }
    
    private fun observeUpcomingEventsViewModel() {
        allEventsViewModel.getAllEventsLiveData().observe(this, ::showResult)
    }

    private fun showResult(upcomingEventListItem: List<UpcomingEventListItem>) {
        branchAdapter.setList(upcomingEventListItem)
    }

    private fun navigateToUpcomingEventsActivity() {
        finish()
    }

    override fun onFavouriteClick(eventApiData: EventApiData) {
        allEventsViewModel.onFavouriteClick(eventApiData)
    }

    private fun navigateToFavouriteEventsActivity() {
        val upcomingEventsScreenIntent = Intent(this, FavouriteEventsActivity::class.java)
        startActivity(upcomingEventsScreenIntent)
    }

    override fun onBranchClicked(branchId: Int?, title: String?) {
    }

    override fun onEventClick(branchId: Int?) {
        val eventDetailsActivity = eventDetailsRouter.createIntent(context = this, branchId)
        startActivity(eventDetailsActivity)
    }
}