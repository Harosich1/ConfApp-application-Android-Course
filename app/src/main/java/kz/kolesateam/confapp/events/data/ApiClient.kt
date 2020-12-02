package kz.kolesateam.confapp.events.data

import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiClient {

    @GET("/upcoming_events")
    fun getUpcomingEventsSync(): Call<ResponseBody>

    @GET("/upcoming_events")
    fun getUpcomingEvents(): Call<List<BranchApiData>>

    @GET("/branch_events/{branch_id}")
    fun getDirectionEvents(@Path("branch_id") branchId: String?): Call<List<EventApiData>>

}