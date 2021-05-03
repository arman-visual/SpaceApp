package com.avisual.spaceapp

import android.app.Application

class SpaceApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}