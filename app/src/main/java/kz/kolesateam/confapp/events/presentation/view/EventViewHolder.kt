package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.*
import kz.kolesateam.confapp.events.presentation.ClickListener
import kz.kolesateam.confapp.events.presentation.TOAST_TEXT_FOR_DIRECTION
import kz.kolesateam.confapp.events.presentation.TOAST_TEXT_FOR_REPORT

class EventViewHolder (
        itemView: View,
        private val clickListener: ClickListener
) : BaseViewHolder<UpcomingEventListItem>(itemView) {

    private val event: View = itemView.findViewById(R.id.event_card)

//    private val branchTitle: TextView = itemView.findViewById(R.id.direction_title)

    private val eventState: TextView = event.findViewById(R.id.event_state)
    private val eventTimeAndAuditory: TextView = event.findViewById(R.id.time_and_auditory)
    private val nameOfSpeaker: TextView = event.findViewById(R.id.name_of_speaker)
    private val speakerJob: TextView = event.findViewById(R.id.job_of_speaker)
    private val eventDescription: TextView = event.findViewById(R.id.description_of_event)
    private val iconInFavourite: ImageView = event.findViewById(R.id.ic_in_favourite)

    override fun onBind(data: UpcomingEventListItem) {
        val eventApiData: EventApiData = (data as? EventListItem)?.data ?: return

        //branchTitle.text = event.title
        setActionToast(eventApiData, eventApiData.title, eventApiData.id)
        onBindCurrentEvent(eventApiData)
    }

    private fun setActionToast(eventApiData: EventApiData, title: String?, branchId: Int?) {
        /*branchTitle.setOnClickListener{
            clickListener.onClickListenerToast(TOAST_TEXT_FOR_DIRECTION.format(
                    title
            ))
            clickListener.onClickListenerNavigateToActivity(branchId)
        }*/
        event.setOnClickListener{
            clickListener.onClickListenerToast(TOAST_TEXT_FOR_REPORT.format(
                    eventApiData.title
            ))
        }
    }

    private fun onBindCurrentEvent(eventApiData: EventApiData) {

        val eventTimeAndAuditoryString = "%s - %s • %s".format(
                eventApiData.startTime?.dropLast(3),
                eventApiData.endTime?.dropLast(3),
                eventApiData.place,
        )

        eventTimeAndAuditory.text = eventTimeAndAuditoryString
        nameOfSpeaker.text = eventApiData.speaker?.fullName ?: "no name"
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
            }

            else {

                iconInFavourite.setImageResource(R.drawable.favorite_icon_filled)
                iconInFavourite.tag = R.drawable.favorite_icon_filled
            }
        }
    }
}