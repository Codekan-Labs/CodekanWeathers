package com.codekan.weathers

import android.app.Application


class CodekanWeathersApp : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInitializer(DatabaseDriverFactory(applicationContext)).initKoin()
    }
}