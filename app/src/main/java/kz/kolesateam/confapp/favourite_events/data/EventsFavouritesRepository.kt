package kz.kolesateam.confapp.favourite_events.data

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.MapType
import com.fasterxml.jackson.databind.type.TypeFactory
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.favourite_events.domain.FavouritesRepository
import java.io.FileInputStream

private const val FAVOURITE_EVENTS_FILE_NAME = "favourite_events.json"

class EventsFavouritesRepository(
    private val applicationContext: Context,
    private val objectMapper: ObjectMapper
) : FavouritesRepository {

    private var favouritesEvents: MutableMap<Int, EventApiData>

    init {
        val favouriteEventsFromFile: Map<Int, EventApiData> = getFavouriteEventsFromFile()
        favouritesEvents = mutableMapOf<Int, EventApiData>()
        favouritesEvents.putAll(favouriteEventsFromFile)
    }

    override fun saveFavourite(eventApiData: EventApiData) {
        eventApiData.id ?: return

        favouritesEvents[eventApiData.id] = eventApiData

        saveFavouriteEventsToFile()
    }

    override fun removeFavouriteEvent(eventId: Int?) {
        favouritesEvents.remove(eventId)
        saveFavouriteEventsToFile()
    }

    override fun getAllFavouriteEvents(): List<EventApiData> {
        return favouritesEvents.values.toList()
    }

    private fun saveFavouriteEventsToFile() {
        val favouriteEventsJsonString: String = objectMapper.writeValueAsString(favouritesEvents)
        val filOutputStream = applicationContext.openFileOutput(
            FAVOURITE_EVENTS_FILE_NAME,
            Context.MODE_PRIVATE
        )
        filOutputStream.write(favouriteEventsJsonString.toByteArray())
        filOutputStream.close()
    }

    private fun getFavouriteEventsFromFile(): Map<Int, EventApiData> {
        var fileInputStream: FileInputStream? = null

        try {
            fileInputStream = applicationContext.openFileInput(FAVOURITE_EVENTS_FILE_NAME)
        } catch (exception: Exception) {
            fileInputStream?.close()
            return emptyMap()
        }

        val favouriteEventsJsonString: String =
            fileInputStream?.bufferedReader()?.readLines()?.joinToString().orEmpty()
        val mapType: MapType = objectMapper.typeFactory.constructMapType(
            Map::class.java,
            Int::class.java,
            EventApiData::class.java
        )
        return objectMapper.readValue(favouriteEventsJsonString, mapType)
    }
}