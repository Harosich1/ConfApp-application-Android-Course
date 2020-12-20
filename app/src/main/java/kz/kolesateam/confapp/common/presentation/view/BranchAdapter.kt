package kz.kolesateam.confapp.common.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allEvents.presentation.view.AllEventsHeaderViewHolder
import kz.kolesateam.confapp.allEvents.presentation.view.EventViewHolder
import kz.kolesateam.confapp.common.presentation.domain.BaseViewHolder
import kz.kolesateam.confapp.common.interaction.BranchListener
import kz.kolesateam.confapp.common.interaction.EventListener
import kz.kolesateam.confapp.common.interaction.FavoriteListener
import kz.kolesateam.confapp.common.presentation.models.*
import kz.kolesateam.confapp.favourite_events.domain.FavouriteEventActionObservable
import kz.kolesateam.confapp.favourite_events.view.FavouriteEventsViewHolder
import kz.kolesateam.confapp.upcomingEvents.presentation.view.BranchViewHolder
import kz.kolesateam.confapp.upcomingEvents.presentation.view.UpcomingHeaderViewHolder

class BranchAdapter(
    private val eventBranchListener: BranchListener,
    private val eventFavoriteListener: FavoriteListener,
    private val eventListener: EventListener,
    private val favouriteEventActionObservable: FavouriteEventActionObservable?
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
        (holder as? EventViewHolder)?.onViewRecycled()
        (holder as? FavouriteEventsViewHolder)?.onViewRecycled()
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
        eventBranchListener,
        eventFavoriteListener,
        favouriteEventActionObservable!!,
        eventListener
    )

    private fun createAllEventsViewHolder(
        parent: ViewGroup
    ): BaseViewHolder<UpcomingEventListItem> = EventViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.event_card_layout,
            parent,
            false
        ),
        eventBranchListener,
        eventFavoriteListener,
        favouriteEventActionObservable!!,
        eventListener
    )

    private fun createFavouriteEventsViewHolder(
        parent: ViewGroup
    ): BaseViewHolder<UpcomingEventListItem> = FavouriteEventsViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.event_card_layout,
            parent,
            false
        ),
        eventFavoriteListener,
        favouriteEventActionObservable!!,
        eventListener
    )
}
