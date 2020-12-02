package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.BranchListItem
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.presentation.ClickListener
import kz.kolesateam.confapp.events.presentation.TOAST_TEXT_FOR_DIRECTION
import kz.kolesateam.confapp.events.presentation.TOAST_TEXT_FOR_REPORT

class BranchViewHolder(
        itemView: View,
        private val clickListener: ClickListener
) : BaseViewHolder<UpcomingEventListItem>(itemView) {

    private val branchCurrentEvent: View = itemView.findViewById(R.id.branch_current_event)
    private val branchNextEvent: View = itemView.findViewById(R.id.branch_next_event)

    private val branchTitle: TextView = itemView.findViewById(R.id.branch_title)
    private val branchArrowTransition: ImageView = itemView.findViewById(R.id.about_branch)

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

    override fun onBind(data: UpcomingEventListItem) {
        val branchApiData: BranchApiData = (data as? BranchListItem)?.data ?: return

        branchTitle.text = branchApiData.title

        val currentEvent: EventApiData = branchApiData.events.first()
        val nextEvent: EventApiData = branchApiData.events.last()

        setActionToast(currentEvent, nextEvent, branchApiData.title, branchApiData.id)
        onBindCurrentEvent(currentEvent)
        onBindEventNext(nextEvent)
    }

    private fun setActionToast(currentEvent: EventApiData, nextEvent: EventApiData, title: String?, branchId: Int?) {
        branchTitle.setOnClickListener{
            clickListener.onClickListenerToast(TOAST_TEXT_FOR_DIRECTION.format(
                    title
            ))
            clickListener.onClickListenerNavigateToActivity(branchId, title)
        }
        branchArrowTransition.setOnClickListener{
            clickListener.onClickListenerToast(TOAST_TEXT_FOR_DIRECTION.format(
                    title
            ))
            clickListener.onClickListenerNavigateToActivity(branchId, title)
        }
        branchCurrentEvent.setOnClickListener{
            clickListener.onClickListenerToast(TOAST_TEXT_FOR_REPORT.format(
                    currentEvent.title
            ))
        }
        branchNextEvent.setOnClickListener{
            clickListener.onClickListenerToast(TOAST_TEXT_FOR_REPORT.format(
                    nextEvent.title
            ))
        }
    }

    private fun onBindCurrentEvent(currentEvent: EventApiData) {

        val currentEventTimeAndAuditoryString = "%s - %s • %s".format(
                currentEvent.startTime?.dropLast(3),
                currentEvent.endTime?.dropLast(3),
                currentEvent.place,
        )

        eventTimeAndAuditoryCurrent.text = currentEventTimeAndAuditoryString
        nameOfSpeakerCurrent.text = currentEvent.speaker?.fullName ?: "no name"
        speakerJobCurrent.text = currentEvent.speaker?.job
        eventDescriptionCurrent.text = currentEvent.title

        setActionForChangeStateOfLikeButton(iconInFavouriteCurrent)
    }

    private fun onBindEventNext(nextEvent: EventApiData) {

        val nextEventTimeAndAuditoryString = "%s - %s • %s".format(
                nextEvent.startTime?.dropLast(3),
                nextEvent.endTime?.dropLast(3),
                nextEvent.place,
        )

        eventTimeAndAuditoryNext.text = nextEventTimeAndAuditoryString
        nameOfSpeakerNext.text = nextEvent.speaker?.fullName ?: "no name"
        speakerJobNext.text = nextEvent.speaker?.job
        eventDescriptionNext.text = nextEvent.title

        setActionForChangeStateOfLikeButton(iconInFavouriteNext)
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