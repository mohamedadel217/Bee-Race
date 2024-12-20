package com.example.bee_race

import android.app.Application
import com.example.bee_race.AppModule.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class BeeRaceApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BeeRaceApplication)
            modules(appModule)
        }
    }
}