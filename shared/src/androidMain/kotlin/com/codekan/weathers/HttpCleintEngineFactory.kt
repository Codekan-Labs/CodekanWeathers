package com.codekan.weathers

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android

actual fun provideHttpClientEngine(): HttpClientEngine {
    return Android.create()
}