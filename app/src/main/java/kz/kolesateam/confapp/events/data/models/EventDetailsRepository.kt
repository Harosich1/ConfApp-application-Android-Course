package kz.kolesateam.confapp.events.data.models

import android.util.Log
import androidx.annotation.Nullable
import kz.kolesateam.confapp.events.data.datasource.EventsDataSource
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