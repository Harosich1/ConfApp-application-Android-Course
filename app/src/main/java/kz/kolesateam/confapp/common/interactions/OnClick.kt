package kz.kolesateam.confapp.common.interactions

import kz.kolesateam.confapp.common.models.EventApiData

interface OnClick {
    fun onFavouriteClick(eventApiData: EventApiData)
}