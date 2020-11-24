package kz.kolesateam.confapp.events.presentation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fasterxml.jackson.databind.JsonNode
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.SpeakerApiData
import kz.kolesateam.confapp.events.presentation.view.BranchAdapter
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
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

class UpcomingEventsActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var branchAdapter: BranchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)

        bindViews()
        loadApiData()
    }

    private fun bindViews(){
        recyclerView = findViewById(R.id.upcoming_event_activity_recycler)
        branchAdapter = BranchAdapter()
        recyclerView.adapter = branchAdapter
    }

    private fun loadApiData(){
        apiClient.getUpcomingEvents().enqueue(object : Callback<List<BranchApiData>> {
            override fun onResponse(call: Call<List<BranchApiData>>, response: Response<List<BranchApiData>>) {
                if(response.isSuccessful){
                    branchAdapter.setList(response.body()!!)
                }
            }

            override fun onFailure(call: Call<List<BranchApiData>>, t: Throwable) {

            }

        })
    }
}


