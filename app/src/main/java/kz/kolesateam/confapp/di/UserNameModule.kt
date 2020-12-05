package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.events.data.datasource.UserNameDataSource
import kz.kolesateam.confapp.events.data.datasource.UserNameLocalDataSource
import kz.kolesateam.confapp.events.data.datasource.UserNameMemoryDataSource
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val SHARED_PREFS_DATA_SOURCE = "shared_prefs_data_source"
const val MEMORY_DATA_SOURCE = "memory_data_source"

val userNameModule: Module = module {

    single(named(MEMORY_DATA_SOURCE)) {
        UserNameMemoryDataSource() as UserNameDataSource
    }

    factory(named(SHARED_PREFS_DATA_SOURCE)) {
        UserNameLocalDataSource(
                sharedPreferences = get()
        ) as UserNameDataSource
    }
}