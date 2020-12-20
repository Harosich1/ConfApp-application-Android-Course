package kz.kolesateam.confapp.upcomingEvents.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import org.threeten.bp.ZonedDateTime
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.presentation.domain.BaseViewHolder
import kz.kolesateam.confapp.common.models.BranchApiData
import kz.kolesateam.confapp.common.models.EventApiData
import kz.kolesateam.confapp.common.presentation.models.BranchListItem
import kz.kolesateam.confapp.common.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.favourite_events.domain.FavouriteEventActionObservable
import kz.kolesateam.confapp.favourite_events.domain.model.FavouriteActionEvent
import java.util.*
import kz.kolesateam.confapp.common.interactions.BranchListener
import kz.kolesateam.confapp.common.interactions.EventListener
import kz.kolesateam.confapp.common.interactions.FavoriteListener
import kz.kolesateam.confapp.utils.DATE_OF_EVENT
import kz.kolesateam.confapp.utils.extensions.getEventFormattedDateTime

class BranchViewHolder(
    itemView: View,
    private val branchListener: BranchListener,
    private val favoriteListener: FavoriteListener,
    private val favouriteEventActionObservable: FavouriteEventActionObservable,
    private val eventListener: EventListener
) : BaseViewHolder<UpcomingEventListItem>(itemView) {

    private val favouriteObserver: Observer = object : Observer {
        override fun update(p0: Observable?, favouriteEventActionObject: Any?) {
            val favouriteEventAction =
                (favouriteEventActionObject as? FavouriteActionEvent) ?: return

            if (branchApiData.events.isEmpty()) return

            val firstEvent = branchApiData.events.first()
            val lastEvent = branchApiData.events.last()

            if (firstEvent.id == favouriteEventAction.eventId) {
                favoriteImageViewCurrent.setImageResource(
                    getFavouriteImageResource(favouriteEventAction.isFavourite)
                )
            }

            if (lastEvent.id == favouriteEventAction.eventId) {
                favoriteImageViewNext.setImageResource(
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
    private val eventTimeAndAuditoryCurrent: TextView =
        branchCurrentEvent.findViewById(R.id.time_and_auditory)
    private val nameOfSpeakerCurrent: TextView =
        branchCurrentEvent.findViewById(R.id.name_of_speaker)
    private val speakerJobCurrent: TextView = branchCurrentEvent.findViewById(R.id.job_of_speaker)
    private val eventDescriptionCurrent: TextView =
        branchCurrentEvent.findViewById(R.id.title_of_event)
    private val favoriteImageViewCurrent: ImageView =
        branchCurrentEvent.findViewById(R.id.ic_in_favourite)

    private val eventStateNext: TextView = branchNextEvent.findViewById(R.id.event_state)
    private val eventTimeAndAuditoryNext: TextView =
        branchNextEvent.findViewById(R.id.time_and_auditory)
    private val nameOfSpeakerNext: TextView = branchNextEvent.findViewById(R.id.name_of_speaker)
    private val speakerJobNext: TextView = branchNextEvent.findViewById(R.id.job_of_speaker)
    private val eventDescriptionNext: TextView =
        branchNextEvent.findViewById(R.id.title_of_event)
    private val favoriteImageViewNext: ImageView = branchNextEvent.findViewById(R.id.ic_in_favourite)

    private lateinit var branchApiData: BranchApiData

    init {
        branchCurrentEvent.findViewById<TextView>(R.id.event_state).visibility = View.INVISIBLE
    }

    override fun onBind(data: UpcomingEventListItem) {
        branchApiData = (data as? BranchListItem)?.data ?: return

        branchTitle.text = branchApiData.title

        if (branchApiData.events.isEmpty()) {
            branchCurrentEvent.visibility = View.GONE
            branchNextEvent.visibility = View.GONE

            return
        }

        currentEvent = branchApiData.events.first()
        nextEvent = branchApiData.events.last()

        setAction(branchApiData.title, branchApiData.id)
        onBindCurrentEvent(currentEvent)
        onBindEventNext(nextEvent)
        setNavigateToEventDetails(currentEvent.id, nextEvent.id)

        favouriteEventActionObservable.subscribe(favouriteObserver)
    }

    fun onViewRecycled() {
        favouriteEventActionObservable.unsubscribe(favouriteObserver)
    }

    private fun setAction(
        title: String?,
        branchId: Int?
    ) {
        branchTitle.setOnClickListener {
            branchListener.onBranchClicked(branchId, title)
        }
        branchArrowTransition.setOnClickListener {
            branchListener.onBranchClicked(branchId, title)
        }
    }

    private fun setNavigateToEventDetails(CurrentBranchId: Int?, NextBranchId: Int?) {
        branchCurrentEvent.setOnClickListener {
            eventListener.onEventClick(CurrentBranchId)
        }
        branchNextEvent.setOnClickListener {
            eventListener.onEventClick(NextBranchId)
        }
    }

    private fun onBindCurrentEvent(currentEvent: EventApiData) {
        val formattedStartTime = ZonedDateTime.parse(currentEvent.startTime).getEventFormattedDateTime()
        val formattedEndTime = ZonedDateTime.parse(currentEvent.endTime).getEventFormattedDateTime()

        val currentEventTimeAndAuditoryString = DATE_OF_EVENT.format(
            formattedStartTime,
            formattedEndTime,
            currentEvent.place
        )

        eventTimeAndAuditoryCurrent.text = currentEventTimeAndAuditoryString
        nameOfSpeakerCurrent.text = currentEvent.speaker?.fullName ?: ""
        speakerJobCurrent.text = currentEvent.speaker?.job
        eventDescriptionCurrent.text = currentEvent.title

        favoriteImageViewCurrent.setImageResource(getFavouriteImageResource(currentEvent.isFavourite))
        setActionForChangeStateOfLikeButton(favoriteImageViewCurrent, currentEvent)
    }

    private fun onBindEventNext(nextEvent: EventApiData) {

        val formattedStartTime = ZonedDateTime.parse(nextEvent.startTime).getEventFormattedDateTime()
        val formattedEndTime = ZonedDateTime.parse(nextEvent.endTime).getEventFormattedDateTime()

        val nextEventTimeAndAuditoryString = DATE_OF_EVENT.format(
            formattedStartTime,
            formattedEndTime,
            nextEvent.place
        )

        eventTimeAndAuditoryNext.text = nextEventTimeAndAuditoryString
        nameOfSpeakerNext.text = nextEvent.speaker?.fullName ?: ""
        speakerJobNext.text = nextEvent.speaker?.job
        eventDescriptionNext.text = nextEvent.title

        favoriteImageViewNext.setImageResource(getFavouriteImageResource(nextEvent.isFavourite))
        setActionForChangeStateOfLikeButton(favoriteImageViewNext, nextEvent)
    }

    private fun setActionForChangeStateOfLikeButton(
        iconInFavourite: ImageView,
        event: EventApiData
    ) {

        iconInFavourite.setOnClickListener {

            event.isFavourite = !event.isFavourite

            val favouriteImageResource = getFavouriteImageResource(event.isFavourite)

            iconInFavourite.setImageResource(favouriteImageResource)
            favoriteListener.onFavouriteClick(eventApiData = event)
        }
    }

    private fun getFavouriteImageResource(
        isFavourite: Boolean
    ): Int = when (isFavourite) {
        true -> R.drawable.favorite_icon_filled
        else -> R.drawable.favourite_icon_not_filled
    }
}