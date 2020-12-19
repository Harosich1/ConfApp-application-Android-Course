package kz.kolesateam.confapp.common.interaction

import kz.kolesateam.confapp.common.models.EventApiData

interface OnClick {
    fun onFavouriteClick(eventApiData: EventApiData)
}