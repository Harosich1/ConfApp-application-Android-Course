package kz.kolesateam.confapp.upcomingEvents.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allEvents.presentation.AllEventsRouter
import kz.kolesateam.confapp.allEvents.presentation.PUSH_NOTIFICATION_MESSAGE
import kz.kolesateam.confapp.common.models.EventApiData
import kz.kolesateam.confapp.common.interaction.OnBranchClicked
import kz.kolesateam.confapp.common.interaction.OnClick
import kz.kolesateam.confapp.common.interaction.OnClickToastMessage
import kz.kolesateam.confapp.common.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.common.presentation.view.BranchAdapter
import kz.kolesateam.confapp.favourite_events.domain.FavouriteEventActionObservable
import kz.kolesateam.confapp.favourite_events.presentation.FavouriteEventsActivity
import kz.kolesateam.confapp.upcomingEvents.presentation.viewModel.UpcomingEventsViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


const val TOAST_TEXT_FOR_DIRECTION = "Это направление %s!"
const val TOAST_TEXT_FOR_REPORT = "Это доклад %s!"
const val TOAST_TEXT_FOR_ADD_IN_FAVOURITE = "Вы добавили в избранное!"
const val TOAST_TEXT_FOR_REMOVE_FROM_FAVOURITE = "Вы убрали из избранного!"
const val TOAST_TEXT_FOR_ENTER_IN_FAVOURITE = "Это ваше избранное!"

class UpcomingEventsActivity : AppCompatActivity(), OnBranchClicked, OnClick, OnClickToastMessage {

    private val upcomingEventsViewModel: UpcomingEventsViewModel by viewModel()
    private val favouriteEventActionObservable: FavouriteEventActionObservable by inject()
    private val allEventsRouter: AllEventsRouter = AllEventsRouter()

    private lateinit var recyclerView: RecyclerView
    private lateinit var branchAdapter: BranchAdapter
    private lateinit var eventsProgressBar: ProgressBar
    private lateinit var inYourFavouriteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_layout)

        bindViews()
        observeUpcomingEventsViewModel()
        upcomingEventsViewModel.onLaunch()
        eventsProgressBar.visibility = View.GONE
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        val messageFromPush: String? = intent?.getStringExtra(PUSH_NOTIFICATION_MESSAGE)
        messageFromPush?.let {
            Toast.makeText(this, it, Toast.LENGTH_LONG)
        }
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.upcoming_event_activity_recycler)
        eventsProgressBar = findViewById(R.id.events_progress_bar)
        inYourFavouriteButton = findViewById(R.id.all_events_activity_button_in_favourite)

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
        inYourFavouriteButton.setOnClickListener {
            Toast.makeText(this, TOAST_TEXT_FOR_ENTER_IN_FAVOURITE, Toast.LENGTH_LONG).show()
            navigateToFavouriteEventsActivity()
        }
    }

    private fun observeUpcomingEventsViewModel() {
        upcomingEventsViewModel.getUpcomingEventsLiveData().observe(this, ::showResult)
    }

    private fun showResult(upcomingEventListItem: List<UpcomingEventListItem>) {
        branchAdapter.setList(upcomingEventListItem)
    }

    override fun onBranchClicked(branchId: Int?, title: String?) {
        val allEventsScreenIntent = allEventsRouter.createIntent(this, branchId, title)
        startActivity(allEventsScreenIntent)
    }

    private fun navigateToFavouriteEventsActivity() {
        val upcomingEventsScreenIntent = Intent(this, FavouriteEventsActivity::class.java)
        startActivity(upcomingEventsScreenIntent)
    }

    override fun onFavouriteClick(eventApiData: EventApiData) {
        upcomingEventsViewModel.onFavouriteClick(eventApiData)
    }

    override fun onClickToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}