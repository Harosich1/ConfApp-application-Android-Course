package kz.kolesateam.confapp.common.di

import kz.kolesateam.confapp.favourite_events.data.EventsFavouritesRepository
import kz.kolesateam.confapp.favourite_events.domain.FavouritesRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

val favouriteEventsModule: Module = module {
    single {
        EventsFavouritesRepository(
            applicationContext = androidApplication(),
            objectMapper = get()
        ) as FavouritesRepository
    }
}