package com.codekan.weathers.domain.model
import kotlinx.serialization.Serializable
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName
// Serialization is used for data transfer between Kotlin and Swift.
@OptIn(ExperimentalObjCName::class)
@Serializable
// ObjCName is used to expose the class to Swift with a specific name.
@ObjCName("Forecast")
data class Forecast(
    val city: String,
    val country: String,
    val forecastList: List<ForecastItem>
)

@OptIn(ExperimentalObjCName::class)
@Serializable
// ObjCName is used to expose the class to Swift with a specific name.
@ObjCName("ForecastItem")
data class ForecastItem(
    val date: String,
    val temperature: Double,
    val humidity: Int,
    val windSpeed: Double,
    val description: String
)