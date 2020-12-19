package kz.kolesateam.confapp.upcomingEvents.presentation.view

import android.view.View
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.presentation.domain.BaseViewHolder
import kz.kolesateam.confapp.common.presentation.models.UpcomingHeaderItem
import kz.kolesateam.confapp.common.presentation.models.UpcomingEventListItem

class UpcomingHeaderViewHolder(itemView: View) : BaseViewHolder<UpcomingEventListItem>(itemView) {

    private val userNameTextView = itemView.findViewById<TextView>(R.id.user_name_text_view)

    override fun onBind(data: UpcomingEventListItem) {
        val userName: String = (data as? UpcomingHeaderItem)?.userName ?: return
        userNameTextView.text = itemView.resources.getString(R.string.hello_user_fmt).format(userName)
    }
}