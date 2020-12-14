package kz.kolesateam.confapp.events.data.models

import android.util.Log
import kz.kolesateam.confapp.events.data.datasource.EventsDataSource
import kz.kolesateam.confapp.events.presentation.models.BranchListItem
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.utils.HELLO_USER_FORMAT
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "onFailureMessage"

class UpcomingEventsRepository(
        private val eventsDataSource: EventsDataSource
) {

    fun loadApiData(
            result: (List<BranchApiData>) -> Unit
    ) {
        eventsDataSource.getUpcomingEvents().enqueue(object : Callback<List<BranchApiData>> {

            override fun onResponse(call: Call<List<BranchApiData>>, response: Response<List<BranchApiData>>) {
                if (response.isSuccessful) {
                    result(response.body()!!)
                } else {
                    result(emptyList())
                }
            }

            override fun onFailure(call: Call<List<BranchApiData>>, t: Throwable) {
                Log.d(TAG, t.localizedMessage)
            }
        })
    }
}