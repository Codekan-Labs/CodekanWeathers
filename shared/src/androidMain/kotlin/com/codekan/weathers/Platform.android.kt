package com.codekan.weathers

import android.os.Build
import com.codekan.weathers.shared.BuildConfig

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun provideApiKey(): String = BuildConfig.OPEN_WEATHER_API_KEY