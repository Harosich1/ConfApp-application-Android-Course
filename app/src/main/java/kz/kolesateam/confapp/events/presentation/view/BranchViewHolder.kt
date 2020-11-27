package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData

class BranchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val branchCurrentEvent: View = itemView.findViewById(R.id.branch_current_event)
    private val branchNextEvent: View = itemView.findViewById(R.id.branch_next_event)

    private val branchTitleCurrent: TextView = itemView.findViewById(R.id.branch_title)

    private val eventStateCurrent: TextView = branchCurrentEvent.findViewById(R.id.event_state)
    private val eventTimeAndAuditoryCurrent: TextView = branchCurrentEvent.findViewById(R.id.time_and_auditory)
    private val nameOfSpeakerCurrent: TextView = branchCurrentEvent.findViewById(R.id.name_of_speaker)
    private val speakerJobCurrent: TextView = branchCurrentEvent.findViewById(R.id.job_of_speaker)
    private val eventDescriptionCurrent: TextView = branchCurrentEvent.findViewById(R.id.description_of_event)
    private val iconInFavouriteCurrent: ImageView = branchCurrentEvent.findViewById(R.id.ic_in_favourite)

    private val eventStateNext: TextView = branchNextEvent.findViewById(R.id.event_state)
    private val eventTimeAndAuditoryNext: TextView = branchNextEvent.findViewById(R.id.time_and_auditory)
    private val nameOfSpeakerNext: TextView = branchNextEvent.findViewById(R.id.name_of_speaker)
    private val speakerJobNext: TextView = branchNextEvent.findViewById(R.id.job_of_speaker)
    private val eventDescriptionNext: TextView = branchNextEvent.findViewById(R.id.description_of_event)
    private val iconInFavouriteNext: ImageView = branchNextEvent.findViewById(R.id.ic_in_favourite)

    init {
        branchCurrentEvent.findViewById<TextView>(R.id.event_state).visibility = View.INVISIBLE
    }

    fun onBind(branchApiData: BranchApiData){
        branchTitleCurrent.text = branchApiData.title
        val currentEvent: EventApiData = branchApiData.events.first()
        val nextEvent: EventApiData = branchApiData.events.last()

        val currentEventTimeAndAuditoryString = "%s - %s • %s".format(
                currentEvent.startTime,
                currentEvent.endTime,
                currentEvent.place,
        )

        eventTimeAndAuditoryCurrent.text = currentEventTimeAndAuditoryString
        nameOfSpeakerCurrent.text = currentEvent.speaker?.fullName ?: "no name"
        speakerJobCurrent.text = currentEvent.speaker?.job
        eventDescriptionCurrent.text = currentEvent.title
        iconInFavouriteCurrent.tag = R.drawable.favourite_icon_not_filled
        iconInFavouriteCurrent.setOnClickListener {
            if(iconInFavouriteCurrent.tag == R.drawable.favorite_icon_filled) {
                iconInFavouriteCurrent.setImageResource(R.drawable.favourite_icon_not_filled)
                iconInFavouriteCurrent.tag = R.drawable.favourite_icon_not_filled
            }
            else{
                iconInFavouriteCurrent.setImageResource(R.drawable.favorite_icon_filled)
                iconInFavouriteCurrent.tag = R.drawable.favorite_icon_filled
            }
        }


        val nextEventTimeAndAuditoryString = "%s - %s • %s".format(
                nextEvent.startTime,
                nextEvent.endTime,
                nextEvent.place,
        )

        eventTimeAndAuditoryNext.text = nextEventTimeAndAuditoryString
        nameOfSpeakerNext.text = nextEvent.speaker?.fullName ?: "no name"
        speakerJobNext.text = nextEvent.speaker?.job
        eventDescriptionNext.text = nextEvent.title
        iconInFavouriteNext.tag = R.drawable.favourite_icon_not_filled
        iconInFavouriteNext.setOnClickListener {
            if(iconInFavouriteNext.tag == R.drawable.favorite_icon_filled) {
                iconInFavouriteNext.setImageResource(R.drawable.favourite_icon_not_filled)
                iconInFavouriteNext.tag = R.drawable.favourite_icon_not_filled
            }
            else{
                iconInFavouriteNext.setImageResource(R.drawable.favorite_icon_filled)
                iconInFavouriteNext.tag = R.drawable.favorite_icon_filled
            }
        }
    }
}