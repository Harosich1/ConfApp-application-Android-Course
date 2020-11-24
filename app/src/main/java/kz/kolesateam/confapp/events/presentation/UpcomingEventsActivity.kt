package kz.kolesateam.confapp.events.presentation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.fasterxml.jackson.databind.JsonNode
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
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

    private lateinit var responceTextView: TextView
    private lateinit var loadDataButtonSync: Button
    private lateinit var loadDataButtonAsync: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)

        bindViews()
    }

    private fun bindViews(){
        responceTextView = findViewById(R.id.upcoming_events_textView)
        loadDataButtonSync = findViewById(R.id.upcoming_events_syncLoad)
        loadDataButtonAsync = findViewById(R.id.upcoming_events_aSyncLoad)
        progressBar = findViewById(R.id.events_progress_bar)
        loadDataButtonSync.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            loadApiDataSync()
        }
        loadDataButtonAsync.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            loadApiDataAsync()
        }
    }

    private fun loadApiDataSync(){
        Thread{
            Thread.sleep(5000)
            val response: Response<JsonNode> = apiClient.getUpcomingEvents().execute()
            if(response.isSuccessful){
                val body: JsonNode = response.body()!!
                runOnUiThread{
                    progressBar.visibility = View.GONE
                    responceTextView.setTextColor(Color.parseColor("#2196F3"))
                    responceTextView.text = body.toString()
                }
            }
        }.start()
    }

    private fun loadApiDataAsync(){
       apiClient.getUpcomingEvents().enqueue(object :Callback<JsonNode> {
           override fun onResponse(call: Call<JsonNode>, response: Response<JsonNode>) {
               if (response.isSuccessful) {
                   progressBar.visibility = View.GONE
                   responceTextView.setTextColor(Color.parseColor("#4CAF50"))
                   val body: JsonNode = response.body()!!
                   responceTextView.text = body.toString()
               }
           }

           override fun onFailure(call: Call<JsonNode>, t: Throwable) {
               progressBar.visibility = View.GONE
               responceTextView.setTextColor(Color.parseColor("#F44336"))
               responceTextView.text = t.localizedMessage
           }

       })
    }
}