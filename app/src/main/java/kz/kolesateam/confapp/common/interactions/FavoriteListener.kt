package kz.kolesateam.confapp.common.interactions

import kz.kolesateam.confapp.common.models.EventApiData

interface FavoriteListener {
    fun onFavouriteClick(eventApiData: EventApiData)
}