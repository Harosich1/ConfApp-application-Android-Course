package kz.kolesateam.confapp.favourite_events.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.OnBranchClicked
import kz.kolesateam.confapp.events.presentation.OnClick
import kz.kolesateam.confapp.events.presentation.OnClickToastMessage
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.presentation.view.BranchAdapter
import kz.kolesateam.confapp.favourite_events.data.EventsFavouritesRepository
import kz.kolesateam.confapp.favourite_events.viewModels.FavouriteEventsViewModel
import kz.kolesateam.confapp.favourite_events.domain.FavouriteEventActionObservable
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class FavouriteEventsActivity : AppCompatActivity(), OnBranchClicked, OnClick, OnClickToastMessage {

    private val favouriteEventsViewModel: FavouriteEventsViewModel by viewModel()
    private val favouriteEventActionObservable: FavouriteEventActionObservable by inject()

    private lateinit var recyclerView: RecyclerView
    private lateinit var branchAdapter: BranchAdapter
    private lateinit var onMainPageButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_events)

        bindViews()
        observeUpcomingEventsViewModel()
        favouriteEventsViewModel.onLaunch()
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.activity_favourite_events_recycler)
        onMainPageButton = findViewById(R.id.favourite_events_activity_button_on_main_page)

        branchAdapter = BranchAdapter(
            eventOnBranchClicked = this,
            eventOnClick = this,
            eventOnClickToastMessage = this,
            favouriteEventActionObservable = favouriteEventActionObservable
        )

        recyclerView.adapter = branchAdapter
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.setPadding(60, 0, 60, 240)

        onMainPageButton.setOnClickListener {
            navigateToUpcomingEventsActivity()
        }
    }

    private fun observeUpcomingEventsViewModel() {
        favouriteEventsViewModel.getAllEventsLiveData().observe(this, ::showResult)
    }

    private fun showResult(upcomingEventListItem: List<UpcomingEventListItem>) {
        branchAdapter.setList(upcomingEventListItem)
    }

    private fun navigateToUpcomingEventsActivity() {
        finish()
    }

    override fun onBranchClicked(branchId: Int?, title: String?) {
    }

    override fun onFavouriteClick(eventApiData: EventApiData) {
    }

    override fun onClickToastMessage(message: String) {
    }
}