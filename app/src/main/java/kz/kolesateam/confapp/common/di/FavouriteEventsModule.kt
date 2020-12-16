package kz.kolesateam.confapp.common.di

import kz.kolesateam.confapp.favourite_events.viewModels.FavouriteEventsViewModel
import kz.kolesateam.confapp.favourite_events.data.EventsFavouritesRepository
import kz.kolesateam.confapp.favourite_events.domain.FavouriteEventActionObservable
import kz.kolesateam.confapp.favourite_events.domain.FavouritesRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val favouriteEventsModule: Module = module {

    viewModel {
        FavouriteEventsViewModel(
            eventsFavouritesRepository =  get()
        )
    }

    single {
        EventsFavouritesRepository(
            applicationContext = androidApplication(),
            objectMapper = get(),
            favouriteEventActionObservable = get()
        ) as FavouritesRepository
    }

    single {
        FavouriteEventActionObservable()
    }
}