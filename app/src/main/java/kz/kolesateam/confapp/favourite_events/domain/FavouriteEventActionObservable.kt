package kz.kolesateam.confapp.favourite_events.domain

import kz.kolesateam.confapp.favourite_events.domain.model.FavouriteActionEvent
import java.util.*

class FavouriteEventActionObservable: Observable() {

    fun subscribe(favouritesObserver: Observer) {
        addObserver(favouritesObserver)
    }

    fun unsubscribe(favouritesObserver: Observer) {
        deleteObserver(favouritesObserver)
    }

    fun notifyChanged(eventId: Int, isFavourite: Boolean){
        notify(
            FavouriteActionEvent(
                eventId = eventId,
                isFavourite = isFavourite
            )
        )
    }

    private fun notify(favouriteActionEvent: FavouriteActionEvent) {
        setChanged()
        notifyObservers(favouriteActionEvent)
    }
}