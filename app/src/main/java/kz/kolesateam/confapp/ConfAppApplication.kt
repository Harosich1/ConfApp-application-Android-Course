package kz.kolesateam.confapp

import android.app.Application
import kz.kolesateam.confapp.common.di.*
import kz.kolesateam.confapp.notifications.ConfAppNotificationManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ConfAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ConfAppNotificationManager.init(
            applicationContext = this
        )
        startKoin {
            androidContext(this@ConfAppApplication)
            modules(
                eventScreenModule,
                networkModule,
                applicationModule,
                userNameModule,
                favouriteEventsModule
            )
        }
    }
}