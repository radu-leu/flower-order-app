package com.leu.radu.flowerorder

import android.app.Application
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class FlowerOrderApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin(this, listOf(apiModule, viewModelModule))
    }

}