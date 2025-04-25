package com.codekan.weathers.data.api

import com.codekan.weathers.KtorClient
import com.codekan.weathers.data.api.mapper.toDomain
import com.codekan.weathers.data.api.model.WeatherResponse
import com.codekan.weathers.data.api.model.ForecastResponse
import com.codekan.weathers.domain.model.Forecast
import com.codekan.weathers.domain.model.Weather
import com.codekan.weathers.provideApiKey
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import kotlinx.serialization.json.Json

val weatherUrl = "https://api.openweathermap.org/data/2.5/weather"
val forecastUrl = "https://api.openweathermap.org/data/2.5/forecast"
val cityParamKey = "q"
val appIdParamKey = "appid"
val unitsParamKey = "units"
val countOfTheDayParamKey = "cnt"
class WeatherApi(
    private val client: KtorClient = KtorClient // Varsayılan olarak KtorClient kullan
) {
    suspend fun getCurrentWeather(city: String): DataState<Weather> {
        return try {
            val response = client.client.get(weatherUrl) {
                parameter(cityParamKey, city)
                parameter(appIdParamKey, provideApiKey())
                parameter(unitsParamKey, "metric")
            }
            if (response.status == HttpStatusCode.OK) {
                val apiResponse = Json.decodeFromString<WeatherResponse>(response.bodyAsText())
                DataState.Success(apiResponse.toDomain())
            } else {
                DataState.Error(ErrorType.NetworkError(response.status))
            }
        } catch (e: Exception) {
            DataState.Error(ErrorType.Unknown(e.message ?: "Unknown error"))
        }
    }
    suspend fun getForecast(city: String, dayCount : Int): DataState<Forecast> {
        return try {
            val response = client.client.get(forecastUrl) {
                parameter(cityParamKey, city)
                parameter(appIdParamKey, provideApiKey())
                parameter(unitsParamKey, "metric")
                parameter(countOfTheDayParamKey, dayCount)
            }
            if (response.status == HttpStatusCode.OK) {
                val apiResponse = Json.decodeFromString<ForecastResponse>(response.bodyAsText())
                DataState.Success(apiResponse.toDomain())
            } else {
                DataState.Error(ErrorType.NetworkError(response.status))
            }
        } catch (e: Exception) {
            DataState.Error(ErrorType.Unknown(e.message ?: "Unknown error"))
        }
    }
}