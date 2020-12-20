package kz.kolesateam.confapp.allEvents.data

import android.util.Log
import kz.kolesateam.confapp.common.datasource.EventsDataSource
import kz.kolesateam.confapp.common.models.EventApiData
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
        eventsDataSource.getAllEvents(branchId)
            .enqueue(object : Callback<List<EventApiData>> {
                override fun onResponse(
                    call: Call<List<EventApiData>>,
                    response: Response<List<EventApiData>>
                ) {
                    if (response.isSuccessful) {
                        result(response.body()!!)
                    } else {
                        result(emptyList())
                    }
                }

                override fun onFailure(call: Call<List<EventApiData>>, t: Throwable) {
                    Log.d(TAG, t.localizedMessage)
                }
            })
    }
}