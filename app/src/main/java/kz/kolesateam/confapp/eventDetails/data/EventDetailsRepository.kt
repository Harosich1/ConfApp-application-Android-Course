package kz.kolesateam.confapp.eventDetails.data

import android.util.Log
import kz.kolesateam.confapp.common.datasource.EventsDataSource
import kz.kolesateam.confapp.common.models.EventApiData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "onFailureMessage"

class EventDetailsRepository(
    private val eventsDataSource: EventsDataSource
) {

    fun loadApiData(
        branchId: String,
        result: (EventApiData) -> Unit
    ) {
        eventsDataSource.getEvenDetails(branchId)
            .enqueue(object : Callback<EventApiData> {
                override fun onResponse(
                    call: Call<EventApiData>,
                    response: Response<EventApiData>
                ) {
                    if (response.isSuccessful) {
                        result(response.body()!!)
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<EventApiData>, t: Throwable) {
                    Log.d(TAG, t.localizedMessage)
                }
            })
    }
}