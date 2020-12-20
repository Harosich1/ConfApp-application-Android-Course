package kz.kolesateam.confapp.AllEvents.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.presentation.domain.BaseViewHolder
import kz.kolesateam.confapp.common.interactions.OnBranchClicked
import kz.kolesateam.confapp.common.interactions.OnClick
import kz.kolesateam.confapp.common.interactions.OnClickToastMessage
import kz.kolesateam.confapp.common.interactions.OnEventClick
import kz.kolesateam.confapp.common.models.EventApiData
import kz.kolesateam.confapp.common.presentation.models.EventListItem
import kz.kolesateam.confapp.common.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.upcomingEvents.presentation.view.dateOfEvent
import kz.kolesateam.confapp.upcomingEvents.presentation.view.nOfElementsToDrop
import kz.kolesateam.confapp.upcomingEvents.presentation.TOAST_TEXT_FOR_ADD_IN_FAVOURITE
import kz.kolesateam.confapp.upcomingEvents.presentation.TOAST_TEXT_FOR_REMOVE_FROM_FAVOURITE

class EventViewHolder(
    itemView: View,
    private val onBranchClicked: OnBranchClicked,
    private val onItemClick: OnClick,
    private val eventOnClickToastMessage: OnClickToastMessage,
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
        val eventApiData: EventApiData = (data as? EventListItem)?.data ?: return

        event.layoutParams = (event.layoutParams as RecyclerView.LayoutParams).apply {
            width = ConstraintLayout.LayoutParams.MATCH_PARENT
            height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        }

        onBindEvent(eventApiData)
        setNavigateToEventDetails(eventApiData.id)
        setActionToast(eventApiData)
    }

    private fun setActionToast(eventApiData: EventApiData) {
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

            val favouriteToastText = when(event.isFavourite){
                true -> TOAST_TEXT_FOR_ADD_IN_FAVOURITE
                else -> TOAST_TEXT_FOR_REMOVE_FROM_FAVOURITE
            }

            val favouriteImageResource = when(event.isFavourite){
                true -> R.drawable.favorite_icon_filled
                else -> R.drawable.favourite_icon_not_filled
            }

            iconInFavourite.setImageResource(favouriteImageResource)
            eventOnClickToastMessage.onClickToastMessage(favouriteToastText)
            onItemClick.onFavouriteClick(eventApiData = event)
        }
    }
}