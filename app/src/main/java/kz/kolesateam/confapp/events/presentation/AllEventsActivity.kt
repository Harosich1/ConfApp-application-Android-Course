package kz.kolesateam.confapp.events.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.*
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.presentation.view.BranchAdapter
import org.koin.android.ext.android.inject

class AllEventsActivity : AppCompatActivity(), OnBranchClicked, OnClick {

    private val allEventsRepository: AllEventsRepository by inject()

    private lateinit var recyclerView: RecyclerView
    private lateinit var branchAdapter: BranchAdapter
    private lateinit var inYourFavouriteButton: Button
    private lateinit var arrowActionBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_events_layout)

        val branchId: Int? = intent.extras?.getInt("branchId")
        val branchTitle: String? = intent.extras?.getString("branchTitle")

        bindViews()
        setApiData(branchTitle!!, branchId.toString())
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.activity_all_events_recycler)
        arrowActionBack = findViewById(R.id.all_events_activity_navigation_button)
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

        arrowActionBack.setOnClickListener {
            navigateToUpcomingEventsActivity()
        }
        inYourFavouriteButton.setOnClickListener {
            Toast.makeText(this, TOAST_TEXT_FOR_ENTER_IN_FAVOURITE, Toast.LENGTH_LONG).show()
        }
    }

    private fun setApiData(
            branchTitle: String,
            branchId: String
    ) = allEventsRepository.loadApiData(
            branchTitle,
            branchId,
            result = { upcomingEventListItem ->
                setResult(upcomingEventListItem)
            }
    )

    private fun setResult(upcomingEventListItem: List<UpcomingEventListItem>) = branchAdapter.setList(upcomingEventListItem)

    private fun navigateToUpcomingEventsActivity() {
        val upcomingEventsScreenIntent = Intent(this, UpcomingEventsActivity::class.java)
        finish()
        startActivity(upcomingEventsScreenIntent)
    }

    override fun onClick(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onBranchClicked(branchId: Int?, title: String?) {
    }
}