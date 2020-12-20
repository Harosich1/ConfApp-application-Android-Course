package kz.kolesateam.confapp.eventDetails.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.interactions.FavoriteListener
import kz.kolesateam.confapp.common.models.EventApiData
import kz.kolesateam.confapp.utils.DATE_OF_EVENT
import kz.kolesateam.confapp.utils.extensions.getEventFormattedDateTime
import org.threeten.bp.ZonedDateTime

class EventDetailsViewHolder(
    itemView: View,
    private val onItemClick: FavoriteListener
) {

    private val event: View = itemView.findViewById(R.id.event_details)

    private lateinit var eventTitle: TextView
    private lateinit var eventTimeAndAuditory: TextView
    private lateinit var nameOfSpeaker: TextView
    private lateinit var speakerJob: TextView
    private lateinit var eventDescription: TextView
    private lateinit var imageViewSpeakerPhoto: ImageView
    private lateinit var favoriteImageView: ImageView

    private lateinit var eventApiData: EventApiData

    fun onBind(eventApiData: EventApiData) {
        this.eventApiData = eventApiData

        onBindViews()
        onBindEvent(this.eventApiData)
    }

    private fun onBindViews() {
        eventTitle = event.findViewById(R.id.title_of_event)
        eventTimeAndAuditory = event.findViewById(R.id.time_and_auditory)
        nameOfSpeaker = event.findViewById(R.id.name_of_speaker)
        speakerJob = event.findViewById(R.id.job_of_speaker)
        eventDescription = event.findViewById(R.id.event_details_text)
        imageViewSpeakerPhoto = event.findViewById(R.id.speaker_photo)
        favoriteImageView = event.findViewById(R.id.ic_in_favourite)

        favoriteImageView.setImageResource(getFavouriteImageResource(eventApiData.isFavourite))
    }

    private fun onBindEvent(eventApiData: EventApiData) {
        val formattedStartTime = ZonedDateTime.parse(eventApiData.startTime).getEventFormattedDateTime()
        val formattedEndTime = ZonedDateTime.parse(eventApiData.endTime).getEventFormattedDateTime()

        val eventTimeAndAuditoryString = DATE_OF_EVENT.format(
            formattedStartTime,
            formattedEndTime,
            eventApiData.place
        )

        eventTimeAndAuditory.text = eventTimeAndAuditoryString
        nameOfSpeaker.text = eventApiData.speaker?.fullName ?: ""
        speakerJob.text = eventApiData.speaker?.job
        eventTitle.text = eventApiData.title
        eventDescription.text = eventApiData.description

            Glide.with(imageViewSpeakerPhoto.context)
            .load(eventApiData.speaker?.photoUrl)
            .into(imageViewSpeakerPhoto)

        setActionForChangeStateOfLikeButton(favoriteImageView)
    }

    private fun setActionForChangeStateOfLikeButton(iconInFavourite: ImageView) {

        iconInFavourite.setOnClickListener {

            eventApiData.isFavourite = !eventApiData.isFavourite

            iconInFavourite.setImageResource(getFavouriteImageResource(eventApiData.isFavourite))
            onItemClick.onFavouriteClick(eventApiData = eventApiData)
        }
    }

    private fun getFavouriteImageResource(
        isFavourite: Boolean
    ): Int = when (isFavourite) {
        true -> R.drawable.favorite_icon_filled
        else -> R.drawable.favourite_icon_not_filled
    }
}