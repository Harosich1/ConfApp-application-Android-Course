package kz.kolesateam.confapp.common.interaction

import kz.kolesateam.confapp.common.models.EventApiData

interface FavoriteListener {
    fun onFavouriteClick(eventApiData: EventApiData)
}