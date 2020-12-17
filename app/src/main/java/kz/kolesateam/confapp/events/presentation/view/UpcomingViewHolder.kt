package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.presentation.BaseViewHolder
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.models.BranchListItem
import kz.kolesateam.confapp.events.presentation.*
import kz.kolesateam.confapp.events.presentation.listeners.OnBranchClicked
import kz.kolesateam.confapp.events.presentation.listeners.OnClick
import kz.kolesateam.confapp.events.presentation.listeners.OnClickToastMessage
import kz.kolesateam.confapp.events.presentation.listeners.OnEventClick
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.favourite_events.domain.FavouriteEventActionObservable
import kz.kolesateam.confapp.favourite_events.domain.model.FavouriteActionEvent
import java.util.*

const val dateOfEvent = "%s - %s • %s"
const val nOfElementsToDrop = 3

class BranchViewHolder(
    itemView: View,
    private val onBranchClicked: OnBranchClicked,
    private val onItemClick: OnClick,
    private val eventOnClickToastMessage: OnClickToastMessage,
    private val favouriteEventActionObservable: FavouriteEventActionObservable,
    private val onEventClick: OnEventClick
) : BaseViewHolder<UpcomingEventListItem>(itemView) {

    private val favouriteObserver: Observer = object: Observer {
        override fun update(p0: Observable?, favouriteEventActionObject: Any?) {
            val favouriteEventAction = (favouriteEventActionObject as? FavouriteActionEvent) ?: return

            if(branchApiData.events.isEmpty()) return

            val firstEvent = branchApiData.events.first()
            val lastEvent = branchApiData.events.last()

            if(firstEvent.id == favouriteEventAction.eventId) {
                iconInFavouriteCurrent.setImageResource(
                    getFavouriteImageResource(favouriteEventAction.isFavourite)
                )
            }

            if(lastEvent.id == favouriteEventAction.eventId) {
                iconInFavouriteNext.setImageResource(
                    getFavouriteImageResource(favouriteEventAction.isFavourite)
                )
            }
        }
    }

    private lateinit var currentEvent: EventApiData
    private lateinit var nextEvent: EventApiData

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

    private lateinit var branchApiData: BranchApiData

    init {
        branchCurrentEvent.findViewById<TextView>(R.id.event_state).visibility = View.INVISIBLE
    }

    override fun onBind(data: UpcomingEventListItem) {
        branchApiData = (data as? BranchListItem)?.data ?: return

        branchTitle.text = branchApiData.title

        if(branchApiData.events.isEmpty()){
            branchCurrentEvent.visibility = View.GONE
            branchNextEvent.visibility = View.GONE

            return
        }

        currentEvent = branchApiData.events.first()
        nextEvent = branchApiData.events.last()

        onBindCurrentEvent(currentEvent)
        onBindEventNext(nextEvent)
        setNavigateToEventDetails()
        setActionToast(branchApiData.title, branchApiData.id)

        favouriteEventActionObservable.subscribe(favouriteObserver)
    }

    fun onViewRecycled() {
        favouriteEventActionObservable.unsubscribe(favouriteObserver)
    }

    private fun setActionToast(title: String?, branchId: Int?) {
        branchTitle.setOnClickListener {
            eventOnClickToastMessage.onClickToastMessage(TOAST_TEXT_FOR_DIRECTION.format(
                    title
            ))
            onBranchClicked.onBranchClicked(branchId, title)
        }
        branchArrowTransition.setOnClickListener {
            eventOnClickToastMessage.onClickToastMessage(TOAST_TEXT_FOR_DIRECTION.format(
                    title
            ))
            onBranchClicked.onBranchClicked(branchId, title)
        }
    }

    private fun setNavigateToEventDetails() {
        branchCurrentEvent.setOnClickListener {
            onEventClick.onEventClick()
        }
        branchNextEvent.setOnClickListener {
            onEventClick.onEventClick()
        }
    }

    private fun onBindCurrentEvent(currentEvent: EventApiData) {

        val currentEventTimeAndAuditoryString = dateOfEvent.format(
                currentEvent.startTime?.dropLast(nOfElementsToDrop),
                currentEvent.endTime?.dropLast(nOfElementsToDrop),
                currentEvent.place,
        )

        eventTimeAndAuditoryCurrent.text = currentEventTimeAndAuditoryString
        nameOfSpeakerCurrent.text = currentEvent.speaker?.fullName ?: ""
        speakerJobCurrent.text = currentEvent.speaker?.job
        eventDescriptionCurrent.text = currentEvent.title

        setActionForChangeStateOfLikeButton(iconInFavouriteCurrent, currentEvent)
    }

    private fun onBindEventNext(nextEvent: EventApiData) {

        val nextEventTimeAndAuditoryString = dateOfEvent.format(
                nextEvent.startTime?.dropLast(nOfElementsToDrop),
                nextEvent.endTime?.dropLast(nOfElementsToDrop),
                nextEvent.place,
        )

        eventTimeAndAuditoryNext.text = nextEventTimeAndAuditoryString
        nameOfSpeakerNext.text = nextEvent.speaker?.fullName ?: ""
        speakerJobNext.text = nextEvent.speaker?.job
        eventDescriptionNext.text = nextEvent.title

        setActionForChangeStateOfLikeButton(iconInFavouriteNext, nextEvent)
    }

    private fun setActionForChangeStateOfLikeButton(iconInFavourite: ImageView, event: EventApiData) {

        iconInFavourite.setOnClickListener {

            event.isFavourite = !event.isFavourite

            val favouriteToastText = when(event.isFavourite){
                true -> TOAST_TEXT_FOR_ADD_IN_FAVOURITE
                else -> TOAST_TEXT_FOR_REMOVE_FROM_FAVOURITE
            }

            val favouriteImageResource = getFavouriteImageResource(event.isFavourite)

            iconInFavourite.setImageResource(favouriteImageResource)
            eventOnClickToastMessage.onClickToastMessage(favouriteToastText)
            onItemClick.onFavouriteClick(eventApiData = event)
        }
    }

    private fun getFavouriteImageResource(
        isFavourite: Boolean
    ): Int = when(isFavourite){
        true -> R.drawable.favorite_icon_filled
        else -> R.drawable.favourite_icon_not_filled
    }
}