package kz.kolesateam.confapp.events.data.datasource

import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface EventsDataSource {

    @GET("/upcoming_events")
    fun getUpcomingEventsSync(): Call<ResponseBody>

    @GET("/upcoming_events")
    fun getUpcomingEvents(): Call<List<BranchApiData>>

    @GET("/branch_events/{branch_id}")
    fun getAllEvents(@Path("branch_id") branchId: String?): Call<List<EventApiData>>

    @GET("/events/{event_id}")
    fun getEvenDetails(@Path("event_id") branchId: String?): Call<List<EventApiData>>
}