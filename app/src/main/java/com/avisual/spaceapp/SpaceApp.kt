package com.avisual.spaceapp

import android.app.Application
import com.avisual.spaceapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SpaceApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger((Level.ERROR))
            androidContext(this@SpaceApp)
            modules(listOf(appModule, repoModule, dataSource, useCasesModule, scopesModule))
        }
    }
}