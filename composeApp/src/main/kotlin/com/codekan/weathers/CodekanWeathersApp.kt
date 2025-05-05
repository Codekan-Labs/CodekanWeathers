package com.codekan.weathers

import android.app.Application
import com.codekan.weathers.admirals.database.DatabaseDriverFactory
import com.codekan.weathers.admirals.di.KoinInitializer


class CodekanWeathersApp : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInitializer(DatabaseDriverFactory(applicationContext)).initKoin()
    }
}