package kz.kolesateam.confapp.common.di

import kz.kolesateam.confapp.events.data.models.AllEventsRepository
import kz.kolesateam.confapp.events.data.models.UpcomingEventsRepository
import kz.kolesateam.confapp.events.presentation.viewModel.AllEventsViewModel
import kz.kolesateam.confapp.events.presentation.viewModel.UpcomingEventsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val eventScreenModule: Module = module {
    viewModel {
        AllEventsViewModel(
            allEventsRepository = get()
        )
    }

    viewModel {
        UpcomingEventsViewModel(
            upcomingEventsRepository = get(),
            userNameDataSource = get(named(SHARED_PREFS_DATA_SOURCE))
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
}