package kz.kolesateam.confapp.events.presentation.listeners

import kz.kolesateam.confapp.events.data.models.EventApiData

interface OnClick {
    fun onFavouriteClick(eventApiData: EventApiData)
}