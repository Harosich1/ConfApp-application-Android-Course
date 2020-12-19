package kz.kolesateam.confapp.favourite_events.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.presentation.BaseViewHolder
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.listeners.OnEventClick
import kz.kolesateam.confapp.events.presentation.models.FavouriteEventsItem
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.presentation.view.dateOfEvent
import kz.kolesateam.confapp.events.presentation.view.nOfElementsToDrop

class FavouriteEventsViewHolder(
    itemView: View,
    private val onEventClick: OnEventClick
) : BaseViewHolder<UpcomingEventListItem>(itemView) {

    private val event: View = itemView.findViewById(R.id.item_event_card)

    private val eventState: TextView = event.findViewById(R.id.event_state)
    private val eventTimeAndAuditory: TextView = event.findViewById(R.id.time_and_auditory)
    private val nameOfSpeaker: TextView = event.findViewById(R.id.name_of_speaker)
    private val speakerJob: TextView = event.findViewById(R.id.job_of_speaker)
    private val eventDescription: TextView = event.findViewById(R.id.title_of_event)
    private val iconInFavourite: ImageView = event.findViewById(R.id.ic_in_favourite)

    init {
        event.findViewById<TextView>(R.id.event_state).visibility = View.INVISIBLE
    }

    override fun onBind(data: UpcomingEventListItem) {
        val eventApiData: EventApiData = (data as? FavouriteEventsItem)?.data ?: return

        event.layoutParams = (event.layoutParams as RecyclerView.LayoutParams).apply {
            width = ConstraintLayout.LayoutParams.MATCH_PARENT
            height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        }

        onBindEvent(eventApiData)
        setNavigateToEventDetails(eventApiData.id)
    }

    private fun setNavigateToEventDetails(branchId: Int?) {
        event.setOnClickListener {
            onEventClick.onEventClick(branchId)
        }
    }

    private fun onBindEvent(eventApiData: EventApiData) {

        val eventTimeAndAuditoryString = dateOfEvent.format(
            eventApiData.startTime?.dropLast(nOfElementsToDrop),
            eventApiData.endTime?.dropLast(nOfElementsToDrop),
            eventApiData.place,
        )

        eventTimeAndAuditory.text = eventTimeAndAuditoryString
        nameOfSpeaker.text = eventApiData.speaker?.fullName ?: ""
        speakerJob.text = eventApiData.speaker?.job
        eventDescription.text = eventApiData.title

        setActionForChangeStateOfLikeButton(iconInFavourite, eventApiData)
    }

    private fun setActionForChangeStateOfLikeButton(iconInFavourite: ImageView, event: EventApiData) {

        iconInFavourite.setOnClickListener {

            event.isFavourite = !event.isFavourite

            val favouriteImageResource = getFavouriteImageResource(event.isFavourite)

            iconInFavourite.setImageResource(favouriteImageResource)
        }
    }

    private fun getFavouriteImageResource(
        isFavourite: Boolean
    ): Int = when(isFavourite){
        true -> R.drawable.favorite_icon_filled
        else -> R.drawable.favourite_icon_not_filled
    }
}