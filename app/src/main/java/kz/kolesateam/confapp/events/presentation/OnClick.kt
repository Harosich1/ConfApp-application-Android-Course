package kz.kolesateam.confapp.events.presentation

import kz.kolesateam.confapp.events.data.models.EventApiData

interface OnClick {
    fun onFavouriteClick(eventApiData: EventApiData)
}