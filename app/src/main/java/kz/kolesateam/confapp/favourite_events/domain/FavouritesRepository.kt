package kz.kolesateam.confapp.favourite_events.domain

import kz.kolesateam.confapp.events.data.models.EventApiData
import java.util.*

interface FavouritesRepository {
    fun saveFavourite(
        eventApiData: EventApiData
    )

    fun removeFavouriteEvent(
        eventId: Int?
    )

    fun getAllFavouriteEvents(

    ): List<EventApiData>
}