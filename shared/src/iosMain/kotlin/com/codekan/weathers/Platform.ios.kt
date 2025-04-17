package com.codekan.weathers

import platform.UIKit.UIDevice
import platform.Foundation.NSBundle


class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun provideApiKey(): String {
    return NSBundle.mainBundle.objectForInfoDictionaryKey("OpenWeatherApiKey") as? String ?: "default_key"
}