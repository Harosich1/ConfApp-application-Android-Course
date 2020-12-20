package kz.kolesateam.confapp.eventDetails.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.models.EventApiData
import kz.kolesateam.confapp.common.interactions.OnClick
import kz.kolesateam.confapp.upcomingEvents.presentation.view.dateOfEvent
import kz.kolesateam.confapp.upcomingEvents.presentation.view.nOfElementsToDrop

class EventDetailsViewHolder(
    itemView: View,
    private val onItemClick: OnClick
) {

    private val event: View = itemView.findViewById(R.id.event_details)

    private lateinit var eventTitle: TextView
    private lateinit var eventTimeAndAuditory: TextView
    private lateinit var nameOfSpeaker: TextView
    private lateinit var speakerJob: TextView
    private lateinit var eventDescription: TextView
    private lateinit var imageViewSpeakerPhoto: ImageView
    private lateinit var iconInFavourite: ImageView

    fun onBind(eventApiData: EventApiData) {

        onBindViews()
        onBindEvent(eventApiData)
    }

    private fun onBindViews() {
        eventTitle = event.findViewById(R.id.title_of_event)
        eventTimeAndAuditory = event.findViewById(R.id.time_and_auditory)
        nameOfSpeaker = event.findViewById(R.id.name_of_speaker)
        speakerJob = event.findViewById(R.id.job_of_speaker)
        eventDescription = event.findViewById(R.id.event_details_text)
        imageViewSpeakerPhoto = event.findViewById(R.id.speaker_photo)
        iconInFavourite = event.findViewById(R.id.ic_in_favourite)
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
        eventTitle.text = eventApiData.title
        eventDescription.text = eventApiData.description

            Glide.with(imageViewSpeakerPhoto.context)
            .load(eventApiData.speaker?.photoUrl)
            .into(imageViewSpeakerPhoto)

        setActionForChangeStateOfLikeButton(iconInFavourite, eventApiData)
    }

    private fun setActionForChangeStateOfLikeButton(iconInFavourite: ImageView, event: EventApiData) {

        iconInFavourite.setOnClickListener {

            event.isFavourite = !event.isFavourite

            val favouriteImageResource = when(event.isFavourite){
                true -> R.drawable.favorite_icon_filled
                else -> R.drawable.favourite_icon_not_filled
            }

            iconInFavourite.setImageResource(favouriteImageResource)
            onItemClick.onFavouriteClick(eventApiData = event)
        }
    }
}