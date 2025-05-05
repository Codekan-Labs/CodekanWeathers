package com.codekan.weathers.admirals.ktor

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android

actual fun provideHttpClientEngine(): HttpClientEngine {
    return Android.create()
}