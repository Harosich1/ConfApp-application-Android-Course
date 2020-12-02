package kz.kolesateam.confapp.events.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
import kz.kolesateam.confapp.events.data.models.*
import kz.kolesateam.confapp.events.presentation.view.BranchAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

private const val TAG = "onFailureMessage"

class DirectionActivity : AppCompatActivity(), ClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var branchAdapter: BranchAdapter
    private lateinit var arrowActionBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direction_layout)

        val branchId: Int? = intent.extras?.getInt("branchId")

        bindViews()
        arrowActionBack.setOnClickListener {
            navigateToUpcomingEventsActivity()
        }
        loadApiData(branchId.toString())
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

    private fun loadApiData(branchId: String?){

        val apiRetrofit: Retrofit = Retrofit.Builder()
                .baseUrl("http://37.143.8.68:2020")
                .addConverterFactory(JacksonConverterFactory.create())
                .build()

        val apiClient: ApiClient = apiRetrofit.create(ApiClient::class.java)

        apiClient.getDirectionEvents(branchId).enqueue(object : Callback<List<EventApiData>> {
            override fun onResponse(call: Call<List<EventApiData>>, response: Response<List<EventApiData>>) {
                if(response.isSuccessful){

                    val upcomingEventListItem: List<UpcomingEventListItem> = getEventListItems(response.body()!!)

                    branchAdapter.setList(upcomingEventListItem)
                }
            }

            override fun onFailure(call: Call<List<EventApiData>>, t: Throwable) {
                recyclerView.visibility = View.GONE
                Log.d(TAG, t.localizedMessage)
            }
        })
    }

    private fun getEventListItems(
            eventList: List<EventApiData>
    ): List<UpcomingEventListItem> = eventList.map { eventApiData -> EventListItem(data = eventApiData) }

    private fun navigateToUpcomingEventsActivity() {
        val directionScreenIntent = Intent(this, UpcomingEventsActivity::class.java)
        startActivity(directionScreenIntent)
    }

    override fun onClickListenerNavigateToActivity(branchId: Int?) {
    }

    override fun onClickListenerToast(TOAST_TEXT: String) {
    }
}