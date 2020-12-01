package kz.kolesateam.confapp.events.data

import kz.kolesateam.confapp.events.data.models.BranchApiData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface ApiClient {

    @GET("/upcoming_events")
    fun getUpcomingEventsSync(): Call<ResponseBody>

    @GET("/upcoming_events")
    fun getUpcomingEvents(): Call<List<BranchApiData>>
}