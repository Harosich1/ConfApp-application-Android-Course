package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.presentation.BaseViewHolder
import kz.kolesateam.confapp.events.data.models.*
import kz.kolesateam.confapp.events.presentation.ClickListener
import kz.kolesateam.confapp.events.presentation.TOAST_TEXT_FOR_ADD_IN_FAVOURITE
import kz.kolesateam.confapp.events.presentation.TOAST_TEXT_FOR_REMOVE_FROM_FAVOURITE
import kz.kolesateam.confapp.events.presentation.TOAST_TEXT_FOR_REPORT
import kz.kolesateam.confapp.events.presentation.models.EventListItem
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem

const val widthOfEventCard = 750

class EventViewHolder (
        itemView: View,
        private val clickListener: ClickListener
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

        event.layoutParams.width = widthOfEventCard

        setActionToast(eventApiData)
        onBindEvent(eventApiData)
    }

    private fun setActionToast(eventApiData: EventApiData) {
        event.setOnClickListener{
            clickListener.onClick(TOAST_TEXT_FOR_REPORT.format(
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
                clickListener.onClick(TOAST_TEXT_FOR_REMOVE_FROM_FAVOURITE)
            }

            else {

                iconInFavourite.setImageResource(R.drawable.favorite_icon_filled)
                iconInFavourite.tag = R.drawable.favorite_icon_filled
                clickListener.onClick(TOAST_TEXT_FOR_ADD_IN_FAVOURITE)
            }
        }
    }
}