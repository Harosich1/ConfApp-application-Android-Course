package kz.kolesateam.confapp.events.data.models

import android.util.Log
import kz.kolesateam.confapp.events.data.datasource.EventsDataSource
import kz.kolesateam.confapp.events.presentation.models.AllEventsHeaderItem
import kz.kolesateam.confapp.events.presentation.models.EventListItem
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "onFailureMessage"

class AllEventsRepository(
    private val eventsDataSource: EventsDataSource
) {

    fun loadApiData(
        branchId: String,
        result: (List<EventApiData>) -> Unit
    ) {
        eventsDataSource.getDirectionEvents(branchId)
            .enqueue(object : Callback<List<EventApiData>> {
                override fun onResponse(
                    call: Call<List<EventApiData>>,
                    response: Response<List<EventApiData>>
                ) {
                    if (response.isSuccessful) {
                        result(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<List<EventApiData>>, t: Throwable) {
                    Log.d(TAG, t.localizedMessage)
                }
            })
    }
}