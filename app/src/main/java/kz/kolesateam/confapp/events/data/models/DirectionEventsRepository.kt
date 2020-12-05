package kz.kolesateam.confapp.events.data.models

import android.util.Log
import android.view.View
import kz.kolesateam.confapp.events.data.datasource.EventsDataSource
import kz.kolesateam.confapp.events.presentation.models.DirectionHeaderItem
import kz.kolesateam.confapp.events.presentation.models.EventListItem
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "onFailureMessage"

class DirectionEventsRepository (
        private val eventsDataSource: EventsDataSource
) {

    fun loadApiData(
            branchTitle: String,
            branchId: String,
            result: (List<UpcomingEventListItem>) -> Unit
    ){
        eventsDataSource.getDirectionEvents(branchId).enqueue(object : Callback<List<EventApiData>> {
            override fun onResponse(call: Call<List<EventApiData>>, response: Response<List<EventApiData>>) {
                if(response.isSuccessful){

                    val upcomingEventListItem: List<UpcomingEventListItem> =

                            listOf(getDirectionHeaderItem(branchTitle)) + getEventListItems(response.body()!!)

                    result(upcomingEventListItem)
                }
            }

            override fun onFailure(call: Call<List<EventApiData>>, t: Throwable) {
                Log.d(TAG, t.localizedMessage)
            }
        })
    }

    private fun getEventListItems(
            eventList: List<EventApiData>
    ): List<UpcomingEventListItem> = eventList.map { eventApiData -> EventListItem(data = eventApiData) }

    private fun getDirectionHeaderItem(branchTitle: String): DirectionHeaderItem = DirectionHeaderItem (
            directionTitle = branchTitle
    )

}