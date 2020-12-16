package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.presentation.BaseViewHolder
import kz.kolesateam.confapp.events.data.models.*
import kz.kolesateam.confapp.events.presentation.*
import kz.kolesateam.confapp.events.presentation.models.EventListItem
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem

class EventViewHolder(
        itemView: View,
        private val onBranchClicked: OnBranchClicked,
        private val onItemClick: OnClick,
        private val eventOnClickToastMessage: OnClickToastMessage
) : BaseViewHolder<UpcomingEventListItem>(itemView) {

    private val event: View = itemView.findViewById(R.id.item_event_card)

    private val eventState: TextView = event.findViewById(R.id.event_state)
    private val eventTimeAndAuditory: TextView = event.findViewById(R.id.time_and_auditory)
    private val nameOfSpeaker: TextView = event.findViewById(R.id.name_of_speaker)
    private val speakerJob: TextView = event.findViewById(R.id.job_of_speaker)
    private val eventDescription: TextView = event.findViewById(R.id.description_of_event)
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

        setActionToast(eventApiData)
        onBindEvent(eventApiData)
    }

    private fun setActionToast(eventApiData: EventApiData) {
        event.setOnClickListener {
            eventOnClickToastMessage.onClickToastMessage(TOAST_TEXT_FOR_REPORT.format(
                    eventApiData.title
            ))
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

        setActionForChangeStateOfLikeButton(iconInFavourite)
    }

    private fun setActionForChangeStateOfLikeButton(iconInFavourite: ImageView) {
        iconInFavourite.tag = R.drawable.favourite_icon_not_filled

        iconInFavourite.setOnClickListener {
            if (iconInFavourite.tag == R.drawable.favorite_icon_filled) {
                iconInFavourite.setImageResource(R.drawable.favourite_icon_not_filled)
                iconInFavourite.tag = R.drawable.favourite_icon_not_filled
                eventOnClickToastMessage.onClickToastMessage(TOAST_TEXT_FOR_REMOVE_FROM_FAVOURITE)
            } else {
                iconInFavourite.setImageResource(R.drawable.favorite_icon_filled)
                iconInFavourite.tag = R.drawable.favorite_icon_filled
                eventOnClickToastMessage.onClickToastMessage(TOAST_TEXT_FOR_ADD_IN_FAVOURITE)
            }
        }
    }
}