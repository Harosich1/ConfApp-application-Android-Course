package kz.kolesateam.confapp.favourite_events.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.favourite_events.domain.FavouritesRepository
import org.koin.android.ext.android.inject

class FavouriteEventsActivity : AppCompatActivity() {

    private val eventsFavouritesRepository: FavouritesRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_events)

        val textView: TextView = findViewById(R.id.favouriteTextView)
        textView.text = eventsFavouritesRepository.getAllFavouriteEvents().toString()
    }
}