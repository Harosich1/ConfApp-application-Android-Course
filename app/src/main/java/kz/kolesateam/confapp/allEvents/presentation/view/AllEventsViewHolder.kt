package kz.kolesateam.confapp.allEvents.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.interaction.OnBranchClicked
import kz.kolesateam.confapp.common.interaction.OnClick
import kz.kolesateam.confapp.common.interaction.OnClickToastMessage
import kz.kolesateam.confapp.common.presentation.domain.BaseViewHolder
import kz.kolesateam.confapp.common.models.*
import kz.kolesateam.confapp.common.presentation.models.EventListItem
import kz.kolesateam.confapp.common.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.favourite_events.domain.FavouriteEventActionObservable
import kz.kolesateam.confapp.favourite_events.domain.model.FavouriteActionEvent
import kz.kolesateam.confapp.upcomingEvents.presentation.TOAST_TEXT_FOR_ADD_IN_FAVOURITE
import kz.kolesateam.confapp.upcomingEvents.presentation.TOAST_TEXT_FOR_REMOVE_FROM_FAVOURITE
import kz.kolesateam.confapp.upcomingEvents.presentation.TOAST_TEXT_FOR_REPORT
import kz.kolesateam.confapp.utils.DATE_OF_EVENT
import kz.kolesateam.confapp.utils.extensions.getEventFormattedDateTime
import org.threeten.bp.ZonedDateTime

class EventViewHolder(
    itemView: View,
    private val onBranchClicked: OnBranchClicked,
    private val onItemClick: OnClick,
    private val eventOnClickToastMessage: OnClickToastMessage,
    private val favouriteEventActionObservable: FavouriteEventActionObservable
) : BaseViewHolder<UpcomingEventListItem>(itemView) {

    private val favouriteObserver: Observer = object : Observer {
        override fun update(p0: Observable?, favouriteEventActionObject: Any?) {
            val favouriteEventAction =
                (favouriteEventActionObject as? FavouriteActionEvent) ?: return

            if (eventApiData.id == favouriteEventAction.eventId) {
                iconInFavourite.setImageResource(
                    getFavouriteImageResource(favouriteEventAction.isFavourite)
                )
            }
        }
    }

    private val event: View = itemView.findViewById(R.id.item_event_card)

    private val eventState: TextView = event.findViewById(R.id.event_state)
    private val eventTimeAndAuditory: TextView = event.findViewById(R.id.time_and_auditory)
    private val nameOfSpeaker: TextView = event.findViewById(R.id.name_of_speaker)
    private val speakerJob: TextView = event.findViewById(R.id.job_of_speaker)
    private val eventDescription: TextView = event.findViewById(R.id.description_of_event)
    private val iconInFavourite: ImageView = event.findViewById(R.id.ic_in_favourite)

    private lateinit var eventApiData: EventApiData

    init {
        event.findViewById<TextView>(R.id.event_state).visibility = View.INVISIBLE
    }


    override fun onBind(data: UpcomingEventListItem) {
        eventApiData = (data as? EventListItem)?.data ?: return

        iconInFavourite.setImageResource(getFavouriteImageResource(eventApiData.isFavourite))

        event.layoutParams = (event.layoutParams as RecyclerView.LayoutParams).apply {
            width = ConstraintLayout.LayoutParams.MATCH_PARENT
            height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        }

        setActionToast(eventApiData)
        onBindEvent(eventApiData)

        favouriteEventActionObservable.subscribe(favouriteObserver)
    }

    fun onViewRecycled() {
        favouriteEventActionObservable.unsubscribe(favouriteObserver)
    }

    private fun setActionToast(eventApiData: EventApiData) {
        event.setOnClickListener {
            eventOnClickToastMessage.onClickToastMessage(
                TOAST_TEXT_FOR_REPORT.format(
                    eventApiData.title
                )
            )
        }
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
        eventDescription.text = eventApiData.title

        setActionForChangeStateOfLikeButton(iconInFavourite, eventApiData)
    }

    private fun setActionForChangeStateOfLikeButton(
        iconInFavourite: ImageView,
        event: EventApiData
    ) {

        iconInFavourite.setOnClickListener {

            event.isFavourite = !event.isFavourite

            val favouriteToastText = when (event.isFavourite) {
                true -> TOAST_TEXT_FOR_ADD_IN_FAVOURITE
                else -> TOAST_TEXT_FOR_REMOVE_FROM_FAVOURITE
            }

            iconInFavourite.setImageResource(getFavouriteImageResource(event.isFavourite))
            eventOnClickToastMessage.onClickToastMessage(favouriteToastText)
            onItemClick.onFavouriteClick(eventApiData = event)
        }
    }

    private fun getFavouriteImageResource(
        isFavourite: Boolean
    ): Int = when (isFavourite) {
        true -> R.drawable.favorite_icon_filled
        else -> R.drawable.favourite_icon_not_filled
    }
}