package kz.kolesateam.confapp.events.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.AllEventsRouter
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.presentation.view.BranchAdapter
import kz.kolesateam.confapp.events.presentation.viewModel.UpcomingEventsViewModel
import org.koin.android.viewmodel.ext.android.viewModel


private const val TAG = "onFailureMessage"
const val TOAST_TEXT_FOR_DIRECTION = "Это направление %s!"
const val TOAST_TEXT_FOR_REPORT = "Это доклад %s!"
const val TOAST_TEXT_FOR_ADD_IN_FAVOURITE = "Вы добавили в избранное!"
const val TOAST_TEXT_FOR_REMOVE_FROM_FAVOURITE = "Вы убрали из избранного!"
const val TOAST_TEXT_FOR_ENTER_IN_FAVOURITE = "Это ваше избранное!"

class UpcomingEventsActivity : AppCompatActivity(), OnBranchClicked, OnClick {

    private val upcomingEventsViewModel: UpcomingEventsViewModel by viewModel()
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

    private fun bindViews() {
        recyclerView = findViewById(R.id.upcoming_event_activity_recycler)
        eventsProgressBar = findViewById(R.id.events_progress_bar)
        inYourFavouriteButton = findViewById(R.id.all_events_activity_button_in_favourite)

        branchAdapter = BranchAdapter(
                eventOnBranchClicked = this,
                eventOnClick = this
        )
        recyclerView.adapter = branchAdapter
        recyclerView.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        )
        inYourFavouriteButton.setOnClickListener {
            Toast.makeText(this, TOAST_TEXT_FOR_ENTER_IN_FAVOURITE, Toast.LENGTH_LONG).show()
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
        finish()
        startActivity(allEventsScreenIntent)
    }

    override fun onClick(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}