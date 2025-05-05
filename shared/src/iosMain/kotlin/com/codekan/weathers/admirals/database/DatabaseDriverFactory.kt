package com.codekan.weathers.admirals.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.codekan.weathers.shared.database.WeathersDatabase

// Database Factory works WITHOUT context param in iOS due to that code separated for Android and iOS.
// Also SQLDelight has own driver factory suitable for iOS NativeSqliteDriver.
actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(WeathersDatabase.Schema, "weathers.db")
    }
}