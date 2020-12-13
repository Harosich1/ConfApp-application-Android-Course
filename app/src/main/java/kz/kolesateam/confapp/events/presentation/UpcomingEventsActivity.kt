package kz.kolesateam.confapp.events.presentation

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
import kz.kolesateam.confapp.common.di.SHARED_PREFS_DATA_SOURCE
import kz.kolesateam.confapp.events.data.datasource.UserNameDataSource
import kz.kolesateam.confapp.events.data.models.UpcomingEventsRepository
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.presentation.view.BranchAdapter
import kz.kolesateam.confapp.events.presentation.viewModel.AllEventsViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named


private const val TAG = "onFailureMessage"
const val TOAST_TEXT_FOR_DIRECTION = "Это направление %s!"
const val TOAST_TEXT_FOR_REPORT = "Это доклад %s!"
const val TOAST_TEXT_FOR_ADD_IN_FAVOURITE = "Вы добавили в избранное!"
const val TOAST_TEXT_FOR_REMOVE_FROM_FAVOURITE = "Вы убрали из избранного!"
const val TOAST_TEXT_FOR_ENTER_IN_FAVOURITE = "Это ваше избранное!"

class UpcomingEventsActivity : AppCompatActivity(), OnBranchClicked, OnClick {

    private val allEventsViewModel: AllEventsViewModel by viewModel()
    private val upcomingEventsRepository: UpcomingEventsRepository by inject()
    private val userNameLocalDataSource: UserNameDataSource by inject(named(SHARED_PREFS_DATA_SOURCE))

    private lateinit var recyclerView: RecyclerView
    private lateinit var branchAdapter: BranchAdapter
    private lateinit var eventsProgressBar: ProgressBar
    private lateinit var inYourFavouriteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_layout)

        bindViews()
        setApiData()
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

    private fun setApiData() {

        eventsProgressBar.visibility = View.VISIBLE
        upcomingEventsRepository.loadApiData(
                getSavedUser(),
                result = { upcomingEventListItem ->
                    setResult(upcomingEventListItem)
                }
        )
        eventsProgressBar.visibility = View.GONE
    }

    private fun setResult(upcomingEventListItem: List<UpcomingEventListItem>) = branchAdapter.setList(upcomingEventListItem)

    private fun getSavedUser(): String = userNameLocalDataSource.getUserName() ?: ""

    override fun onBranchClicked(branchId: Int?, title: String?) {
        val allEventsScreenIntent = Intent(this, AllEventsActivity::class.java)
        allEventsScreenIntent.putExtra("branchId", branchId)
        allEventsScreenIntent.putExtra("branchTitle", title)
        finish()
        startActivity(allEventsScreenIntent)
    }

    override fun onClick(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}