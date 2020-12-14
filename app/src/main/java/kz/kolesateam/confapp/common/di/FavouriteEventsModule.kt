package kz.kolesateam.confapp.common.di

import kz.kolesateam.confapp.events.data.models.AllEventsRepository
import kz.kolesateam.confapp.events.data.models.UpcomingEventsRepository
import kz.kolesateam.confapp.events.presentation.viewModel.AllEventsViewModel
import kz.kolesateam.confapp.events.presentation.viewModel.UpcomingEventsViewModel
import kz.kolesateam.confapp.favourite_events.data.EventsFavouritesRepository
import kz.kolesateam.confapp.favourite_events.domain.FavouritesRepository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val FavouriteEventsModule: Module = module {
    single {
        EventsFavouritesRepository() as FavouritesRepository
    }
}