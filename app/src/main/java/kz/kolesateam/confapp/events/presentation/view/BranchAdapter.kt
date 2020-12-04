package kz.kolesateam.confapp.events.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.presentation.BaseViewHolder
import kz.kolesateam.confapp.events.presentation.models.UPCOMING_HEADER_TYPE
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.presentation.ClickListener
import kz.kolesateam.confapp.events.presentation.models.BRANCH_TYPE
import kz.kolesateam.confapp.events.presentation.models.EVENT_TYPE

class BranchAdapter(
        private val eventClickListener: ClickListener
) : RecyclerView.Adapter<BaseViewHolder<UpcomingEventListItem>>() {

    private val branchApiDataList: MutableList<UpcomingEventListItem> = mutableListOf()

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): BaseViewHolder<UpcomingEventListItem> {
        return when (viewType) {
            UPCOMING_HEADER_TYPE -> createUpcomingHeaderViewHolder(parent)
            BRANCH_TYPE -> createBranchViewHolder(parent)
            EVENT_TYPE -> createEventViewHolder(parent)
            else -> createDirectionHeaderViewHolder(parent)
        }
    }

    override fun onBindViewHolder(
            holder: BaseViewHolder<UpcomingEventListItem>,
            position: Int
    ) {
        holder.onBind(branchApiDataList[position])
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

    private fun createDirectionHeaderViewHolder(
            parent: ViewGroup
    ): BaseViewHolder<UpcomingEventListItem> = DirectionHeaderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                    R.layout.header_layout_for_direction_layout,
                    parent,
                    false
            )
    )

    private fun createBranchViewHolder(
            parent: ViewGroup
    ): BaseViewHolder<UpcomingEventListItem> = BranchViewHolder(
            LayoutInflater.from(parent.context).inflate(
                    R.layout.branch_item,
                    parent,
                    false
            ),
            eventClickListener
    )

    private fun createEventViewHolder(
            parent: ViewGroup
    ): BaseViewHolder<UpcomingEventListItem> = EventViewHolder(
            LayoutInflater.from(parent.context).inflate(
                    R.layout.event_card_layout,
                    parent,
                    false
            ),
            eventClickListener
    )
}