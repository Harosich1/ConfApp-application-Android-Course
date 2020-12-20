package kz.kolesateam.confapp.common.di

import kz.kolesateam.confapp.allEvents.data.AllEventsRepository
import kz.kolesateam.confapp.eventDetails.data.EventDetailsRepository
import kz.kolesateam.confapp.upcomingEvents.data.UpcomingEventsRepository
import kz.kolesateam.confapp.allEvents.presentation.viewModel.AllEventsViewModel
import kz.kolesateam.confapp.eventDetails.presentation.viewModel.EventDetailsViewModel
import kz.kolesateam.confapp.upcomingEvents.presentation.viewModel.UpcomingEventsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val eventScreenModule: Module = module {
    viewModel {
        AllEventsViewModel(
            allEventsRepository = get(),
            upcomingFavouritesRepository = get(),
            notificationAlarmManager = get()
        )
    }

    viewModel {
        UpcomingEventsViewModel(
            upcomingEventsRepository = get(),
            upcomingFavouritesRepository = get(),
            userNameDataSource = get(named(SHARED_PREFS_DATA_SOURCE)),
            notificationAlarmManager = get()
        )
    }

    viewModel {
        EventDetailsViewModel(
            eventDetailsRepository = get(),
            upcomingFavouritesRepository = get(),
            notificationAlarmManager = get()
        )
    }

    factory {
        AllEventsRepository(
            eventsDataSource = get()
        )
    }

    factory {
        UpcomingEventsRepository(
            eventsDataSource = get()
        )
    }

    factory {
        EventDetailsRepository(
            eventsDataSource = get()
        )
    }
}