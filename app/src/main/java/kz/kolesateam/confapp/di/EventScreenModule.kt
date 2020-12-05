    package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.events.data.datasource.EventsDataSource
import kz.kolesateam.confapp.events.data.models.UpcomingEventsRepository
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

    private const val BASE_URL = "http://37.143.8.68:2020"

val eventScreenModule: Module = module {

    single {
       Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

    single {
        val retrofit: Retrofit = get()

        retrofit.create(EventsDataSource::class.java)
    }

    factory {
        UpcomingEventsRepository (
        eventsDataSource = get()
        )
    }
}