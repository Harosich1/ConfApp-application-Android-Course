package kz.kolesateam.confapp.common.di

import android.content.Context
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

const val APPLICATION_SHARED_PREFERENCES = "application_shared_prefs"

val applicationModule: Module = module {

    single {
        val context = androidApplication()

        context.getSharedPreferences(APPLICATION_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }
}