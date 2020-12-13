package kz.kolesateam.confapp.common.di

import kz.kolesateam.confapp.events.data.models.AllEventsRepository
import kz.kolesateam.confapp.events.data.models.UpcomingEventsRepository
import kz.kolesateam.confapp.events.presentation.viewModel.UpcomingEventsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val eventScreenModule: Module = module {
    viewModel {
        UpcomingEventsViewModel(
            upcomingEventsRepository = get(),
            userNameDataSource = get()
        )
    }

    factory {
        UpcomingEventsRepository(
            eventsDataSource = get()
        )
    }

    factory {
        AllEventsRepository(
            eventsDataSource = get()
        )
    }
}