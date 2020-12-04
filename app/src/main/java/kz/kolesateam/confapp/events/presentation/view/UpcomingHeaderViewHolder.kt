package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.presentation.BaseViewHolder
import kz.kolesateam.confapp.events.presentation.models.UpcomingHeaderItem
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem

class UpcomingHeaderViewHolder(itemView: View) : BaseViewHolder<UpcomingEventListItem>(itemView) {

    private val userNameTextView = itemView.findViewById<TextView>(R.id.user_name_text_view)

    override fun onBind(data: UpcomingEventListItem) {
        val userName: String = (data as? UpcomingHeaderItem)?.userName ?: return
        userNameTextView.text = userName
    }
}