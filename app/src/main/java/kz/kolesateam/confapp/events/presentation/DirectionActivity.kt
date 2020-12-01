package kz.kolesateam.confapp.events.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.presentation.view.BranchAdapter

class DirectionActivity : AppCompatActivity(), ClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var branchAdapter: BranchAdapter
    private lateinit var arrowActionBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direction_layout)

        bindViews()
        arrowActionBack.setOnClickListener {
            navigateToUpcomingEventsActivity()
        }
    }

    private fun bindViews(){
        recyclerView = findViewById(R.id.activity_direction_recycler)
        arrowActionBack = findViewById(R.id.direction_layout_action_back)
        branchAdapter = BranchAdapter(eventClickListener = this)

        recyclerView.adapter = branchAdapter
        recyclerView.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        )
    }

    private fun navigateToUpcomingEventsActivity() {
        val directionScreenIntent = Intent(this, UpcomingEventsActivity::class.java)
        startActivity(directionScreenIntent)
    }

    override fun onClickListener(TOAST_TEXT: String) {

    }
}