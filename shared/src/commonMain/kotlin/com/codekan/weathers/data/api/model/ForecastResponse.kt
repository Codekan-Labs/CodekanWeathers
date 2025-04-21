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
    @SerialName("timezone"   ) var timezone   : Int?    = null,
    @SerialName("sunrise"    ) var sunrise    : Int?    = null,
    @SerialName("sunset"     ) var sunset     : Int?    = null

)

@Serializable
data class DailyForecast (

    @SerialName("dt"         ) var dt         : Int? = null,
    @SerialName("main"       ) var main       : Main? = Main(),
    @SerialName("weather"    ) var weather    : ArrayList<Weather> = arrayListOf(),
    @SerialName("clouds"     ) var clouds     : Clouds? = Clouds(),
    @SerialName("wind"       ) var wind       : Wind? = Wind(),
    @SerialName("visibility" ) var visibility : Int? = null,
    @SerialName("pop"        ) var pop        : Int? = null,
    @SerialName("sys"        ) var sys        : Sys? = Sys(),
    @SerialName("dt_txt"     ) var dtTxt      : String? = null,
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