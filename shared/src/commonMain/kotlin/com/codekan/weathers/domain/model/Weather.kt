package com.codekan.weathers.domain.model
import kotlinx.serialization.Serializable
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName
// Serialization is used for data transfer between Kotlin and Swift.
@OptIn(ExperimentalObjCName::class)
@Serializable
// ObjCName is used to expose the class to Swift with a specific name.
@ObjCName("Weather")
data class Weather(
    val city: String,
    val country: String,
    val temperature: String,
    val humidity: String,
    val windSpeed: String,
    val conditionDescription: String,
    val feelsLike : String,
    val rainMmHour : String,
    val sunriseTime : String,
    val sunsetTime : String,
    val timeZone : Int,
    val cityCountry: String = "$city, $country",
    val iconCode : String
)