package kz.kolesateam.confapp.events.presentation

import android.content.Context
import android.content.SharedPreferences
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.APPLICATION_SHARED_PREFERENCES
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.USER_NAME_KEY
import kz.kolesateam.confapp.events.data.ApiClient
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.presentation.view.BranchAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

val apiRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://37.143.8.68:2020")
        .addConverterFactory(JacksonConverterFactory.create())
        .build()

val apiClient: ApiClient = apiRetrofit.create(ApiClient::class.java)

private const val TAG = "onFailureMessage"

class UpcomingEventsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var branchAdapter: BranchAdapter
    private lateinit var branchAdapterForToast: BranchAdapter
    private lateinit var branchAdapterForDirection: BranchAdapter
    private lateinit var eventsProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_layout)

        bindViews()
        loadApiData()
    }

    private fun bindViews(){
        recyclerView = findViewById(R.id.upcoming_event_activity_recycler)
        eventsProgressBar = findViewById(R.id.events_progress_bar)

        branchAdapter = BranchAdapter()
        branchAdapterForToast = branchAdapter.getBranchAdapterForToast()
        branchAdapterForDirection = branchAdapter.getBranchAdapterForDirectionActivity()
        branchAdapterForToast.setToast(this, "Это %s %s!")
        branchAdapterForDirection.setContextForDirectionActivity(this)

        recyclerView.adapter = branchAdapter
        recyclerView.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        )
    }

    private fun loadApiData(){
        apiClient.getUpcomingEvents().enqueue(object : Callback<List<BranchApiData>> {
            override fun onResponse(call: Call<List<BranchApiData>>, response: Response<List<BranchApiData>>) {
                if(response.isSuccessful){
                    val upcomingEventListItem: MutableList<UpcomingEventListItem> = mutableListOf()
                    val headerListItem: UpcomingEventListItem = UpcomingEventListItem(
                            type = 1,
                            data = resources.getString(R.string.hello_user_fmt, getSavedUser())
                    )

                    val branchListItem: List<UpcomingEventListItem> = response.body()!!.map {
                        branchApiData -> UpcomingEventListItem(
                            type = 2,
                            data = branchApiData
                    )
                    }

                    upcomingEventListItem.add(headerListItem)
                    upcomingEventListItem.addAll(branchListItem)

                    branchAdapter.setList(upcomingEventListItem)
                }
            }

            override fun onFailure(call: Call<List<BranchApiData>>, t: Throwable) {
                recyclerView.visibility = View.GONE
                Log.d(TAG, t.localizedMessage)
            }
        })

        setStatusOfProgressBar()
    }

    private fun getSavedUser(): String {
        val sharedPreferences: SharedPreferences = getSharedPreferences(APPLICATION_SHARED_PREFERENCES, Context.MODE_PRIVATE)

        return sharedPreferences.getString(USER_NAME_KEY, null) ?: resources.getString(R.string.event_screen_if_shared_preferences_is_null_text)
    }

    private fun setStatusOfProgressBar(){
        eventsProgressBar.visibility = View.GONE
    }
}