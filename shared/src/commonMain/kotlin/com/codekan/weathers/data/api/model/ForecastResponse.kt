package com.codekan.weathers.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse (

    @SerialName("city"    ) var city    : City?           = City(),
    @SerialName("cod"     ) var cod     : String?         = null,
    @SerialName("message" ) var message : Double?         = null,
    @SerialName("cnt"     ) var cnt     : Int?            = null,
    @SerialName("list"    ) var list    : ArrayList<DailyForecast> = arrayListOf()

)

@Serializable
data class City (

    @SerialName("id"         ) var id         : Int?    = null,
    @SerialName("name"       ) var name       : String? = null,
    @SerialName("coord"      ) var coord      : Coord?  = Coord(),
    @SerialName("country"    ) var country    : String? = null,
    @SerialName("population" ) var population : Int?    = null,
    @SerialName("timezone"   ) var timezone   : Int?    = null

)

@Serializable
data class DailyForecast (

    @SerialName("dt"         ) var dt        : Int?               = null,
    @SerialName("sunrise"    ) var sunrise   : Int?               = null,
    @SerialName("sunset"     ) var sunset    : Int?               = null,
    @SerialName("temp"       ) var temp      : Temp?              = Temp(),
    @SerialName("feels_like" ) var feelsLike : FeelsLike?         = FeelsLike(),
    @SerialName("pressure"   ) var pressure  : Int?               = null,
    @SerialName("humidity"   ) var humidity  : Int?               = null,
    @SerialName("weather"    ) var weather   : ArrayList<Weather> = arrayListOf(),
    @SerialName("speed"      ) var speed     : Double?            = null,
    @SerialName("deg"        ) var deg       : Int?               = null,
    @SerialName("gust"       ) var gust      : Double?            = null,
    @SerialName("clouds"     ) var clouds    : Int?               = null,
    @SerialName("pop"        ) var pop       : Double?            = null,
    @SerialName("rain"       ) var rain      : Double?            = null

)

@Serializable
data class FeelsLike (

    @SerialName("day"   ) var day   : Double? = null,
    @SerialName("night" ) var night : Double? = null,
    @SerialName("eve"   ) var eve   : Double? = null,
    @SerialName("morn"  ) var morn  : Double? = null

)

@Serializable
data class Temp (

    @SerialName("day"   ) var day   : Double? = null,
    @SerialName("min"   ) var min   : Double? = null,
    @SerialName("max"   ) var max   : Double? = null,
    @SerialName("night" ) var night : Double? = null,
    @SerialName("eve"   ) var eve   : Double? = null,
    @SerialName("morn"  ) var morn  : Double? = null

)