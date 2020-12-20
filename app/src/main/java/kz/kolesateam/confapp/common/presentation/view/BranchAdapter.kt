package kz.kolesateam.confapp.common.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.AllEvents.presentation.view.AllEventsHeaderViewHolder
import kz.kolesateam.confapp.AllEvents.presentation.view.EventViewHolder
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.presentation.domain.BaseViewHolder
import kz.kolesateam.confapp.common.interactions.OnBranchClicked
import kz.kolesateam.confapp.common.interactions.OnClick
import kz.kolesateam.confapp.common.interactions.OnClickToastMessage
import kz.kolesateam.confapp.common.interactions.OnEventClick
import kz.kolesateam.confapp.common.presentation.models.ALL_EVENTS_HEADER_TYPE
import kz.kolesateam.confapp.common.presentation.models.BRANCH_TYPE
import kz.kolesateam.confapp.common.presentation.models.EVENT_TYPE
import kz.kolesateam.confapp.common.presentation.models.UPCOMING_HEADER_TYPE
import kz.kolesateam.confapp.common.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.favourite_events.domain.FavouriteEventActionObservable
import kz.kolesateam.confapp.favourite_events.view.FavouriteEventsViewHolder
import kz.kolesateam.confapp.upcomingEvents.presentation.view.BranchViewHolder
import kz.kolesateam.confapp.upcomingEvents.presentation.view.UpcomingHeaderViewHolder

class BranchAdapter(
    private val eventOnBranchClicked: OnBranchClicked,
    private val eventOnClick: OnClick,
    private val eventOnClickToastMessage: OnClickToastMessage,
    private val favouriteEventActionObservable: FavouriteEventActionObservable?,
    private val onEventClick: OnEventClick
) : RecyclerView.Adapter<BaseViewHolder<UpcomingEventListItem>>() {

    private val branchApiDataList: MutableList<UpcomingEventListItem> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<UpcomingEventListItem> {
        return when (viewType) {
            UPCOMING_HEADER_TYPE -> createUpcomingHeaderViewHolder(parent)
            BRANCH_TYPE -> createUpcomingViewHolder(parent)
            EVENT_TYPE -> createAllEventsViewHolder(parent)
            ALL_EVENTS_HEADER_TYPE -> createAllEventsHeaderViewHolder(parent)
            else -> createFavouriteEventsViewHolder(parent)
        }
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<UpcomingEventListItem>,
        position: Int
    ) {
        holder.onBind(branchApiDataList[position])
    }

    override fun onViewRecycled(holder: BaseViewHolder<UpcomingEventListItem>) {
        super.onViewRecycled(holder)
        (holder as? BranchViewHolder)?.onViewRecycled()
    }

    override fun getItemCount(): Int = branchApiDataList.size

    override fun getItemViewType(
        position: Int
    ): Int = branchApiDataList[position].type

    fun setList(branchApiDataList: List<UpcomingEventListItem>) {
        this.branchApiDataList.clear()
        this.branchApiDataList.addAll(branchApiDataList)

        notifyDataSetChanged()
    }

    private fun createUpcomingHeaderViewHolder(
        parent: ViewGroup
    ): BaseViewHolder<UpcomingEventListItem> = UpcomingHeaderViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.header_layout_for_upcoming_layout,
            parent,
            false
        )
    )

    private fun createAllEventsHeaderViewHolder(
        parent: ViewGroup
    ): BaseViewHolder<UpcomingEventListItem> = AllEventsHeaderViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.header_layout_for_all_events_layout,
            parent,
            false
        )
    )

    private fun createUpcomingViewHolder(
        parent: ViewGroup
    ): BaseViewHolder<UpcomingEventListItem> = BranchViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.branch_item,
            parent,
            false
        ),
        eventOnBranchClicked,
        eventOnClick,
        eventOnClickToastMessage,
        favouriteEventActionObservable!!,
        onEventClick
    )

    private fun createAllEventsViewHolder(
        parent: ViewGroup
    ): BaseViewHolder<UpcomingEventListItem> = EventViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.event_card_layout,
            parent,
            false
        ),
        eventOnBranchClicked,
        eventOnClick,
        eventOnClickToastMessage,
        onEventClick
    )

    private fun createFavouriteEventsViewHolder(
        parent: ViewGroup
    ): BaseViewHolder<UpcomingEventListItem> = FavouriteEventsViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.event_card_layout,
            parent,
            false
        ),
        onEventClick
    )
}
