package kz.kolesateam.confapp

import android.app.Application
import kz.kolesateam.confapp.common.di.applicationModule
import kz.kolesateam.confapp.common.di.eventScreenModule
import kz.kolesateam.confapp.common.di.networkModule
import kz.kolesateam.confapp.common.di.userNameModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ConfAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ConfAppApplication)
            modules(
                eventScreenModule,
                networkModule,
                applicationModule,
                userNameModule
            )
        }
    }
}