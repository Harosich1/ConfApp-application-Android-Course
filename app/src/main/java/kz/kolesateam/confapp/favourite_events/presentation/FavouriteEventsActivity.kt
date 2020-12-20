package kz.kolesateam.confapp.favourite_events.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.models.EventApiData
import kz.kolesateam.confapp.common.interaction.OnBranchClicked
import kz.kolesateam.confapp.common.interaction.OnClick
import kz.kolesateam.confapp.common.interaction.OnClickToastMessage
import kz.kolesateam.confapp.common.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.common.presentation.view.BranchAdapter
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
        favouriteEventsViewModel.getAllEventsLiveData().let {
            emptyFavouritesTextView.visibility = View.VISIBLE
            emptyFavouritesImageView.visibility = View.VISIBLE
        }
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
        favouriteEventsViewModel.onFavouriteClick(eventApiData)
    }

    override fun onClickToastMessage(message: String) {
    }
}