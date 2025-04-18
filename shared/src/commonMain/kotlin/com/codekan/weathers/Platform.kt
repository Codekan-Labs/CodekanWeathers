package com.codekan.weathers

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun provideApiKey(): String