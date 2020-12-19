package kz.kolesateam.confapp.common.di

import kz.kolesateam.confapp.common.datasource.UserNameDataSource
import kz.kolesateam.confapp.common.datasource.UserNameLocalDataSource
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val SHARED_PREFS_DATA_SOURCE = "shared_prefs_data_source"

val userNameModule: Module = module {

    factory(named(SHARED_PREFS_DATA_SOURCE)) {
        UserNameLocalDataSource(
                sharedPreferences = get()
        ) as UserNameDataSource
    }
}