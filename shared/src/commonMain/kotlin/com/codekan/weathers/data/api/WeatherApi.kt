package com.codekan.weathers.data.api

import com.codekan.weathers.KtorClient
import com.codekan.weathers.data.api.model.WeatherResponse
import com.codekan.weathers.data.api.model.ForecastResponse
import com.codekan.weathers.provideApiKey
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

val weatherUrl = "https://api.openweathermap.org/data/2.5/weather"
val forecastUrl = "https://api.openweathermap.org/data/2.5/forecast"
val cityParamKey = "q"
val appIdParamKey = "appid"
val unitsParamKey = "units"
val countOfTheDayParamKey = "cnt"
class WeatherApi(
    private val apiKey: String,
    private val client: KtorClient = KtorClient // Varsayılan olarak KtorClient kullan
) {
    suspend fun getCurrentWeather(city: String): Result<WeatherResponse> {
        return try {
            val response = client.client.get(weatherUrl) {
                parameter(cityParamKey, provideApiKey())
                parameter(appIdParamKey, apiKey)
                parameter(unitsParamKey, "metric")
            }
            if (response.status == HttpStatusCode.OK) {
                Result.success(response.body())
            } else {
                Result.failure(Exception("API error: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun getForecast(city: String, dayCount : Int): Result<ForecastResponse> {
        return try {
            val response = client.client.get(forecastUrl) {
                parameter(cityParamKey, city)
                parameter(appIdParamKey, provideApiKey())
                parameter(unitsParamKey, "metric")
                parameter(countOfTheDayParamKey, dayCount)
            }
            if (response.status == HttpStatusCode.OK) {
                Result.success(response.body())
            } else {
                Result.failure(Exception("API error: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}