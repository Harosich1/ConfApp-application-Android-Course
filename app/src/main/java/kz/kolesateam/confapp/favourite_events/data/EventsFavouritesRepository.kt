package kz.kolesateam.confapp.favourite_events.data

import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.favourite_events.domain.FavouritesRepository

class EventsFavouritesRepository: FavouritesRepository{

    private val favouritesEvents: MutableMap<Int, EventApiData> = mutableMapOf()

    override fun saveFavourite(eventApiData: EventApiData) {
        eventApiData.id ?: return

        favouritesEvents[eventApiData.id] = eventApiData
    }

    override fun removeFavouriteEvent(eventId: Int) {
        favouritesEvents.remove(eventId)
    }

    override fun getAllFavouriteEvents(): List<EventApiData> {
        return favouritesEvents.values.toList()
    }

}