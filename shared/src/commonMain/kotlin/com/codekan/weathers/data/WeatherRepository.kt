package com.codekan.weathers.data

import com.codekan.weathers.DatabaseDriverFactory
import com.codekan.weathers.data.api.WeatherApi
import com.codekan.weathers.data.api.mapper.toDomain
import com.codekan.weathers.database.ForecastQueries
import com.codekan.weathers.database.WeatherQueries
import com.codekan.weathers.domain.model.Forecast
import com.codekan.weathers.domain.model.ForecastItem
import com.codekan.weathers.domain.model.Weather
import com.codekan.weathers.shared.database.WeathersDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock


class WeatherRepository(
    private val api: WeatherApi,
    driverFactory: DatabaseDriverFactory
) {
    private val db = WeathersDatabase(driverFactory.createDriver())
    private val weatherQueries: WeatherQueries = db.weatherQueries
    private val forecastQueries: ForecastQueries = db.forecastQueries

    suspend fun getWeather(city: String): Flow<Result<Weather>> = flow {
        // Yerel veritabanından veri al (IO işlemi)
        val localWeather = withContext(Dispatchers.IO) {
            weatherQueries.selectByCity(city).executeAsOneOrNull()
        }
        if (localWeather != null && isDataFresh(localWeather.lastUpdated)) {
            emit(
                Result.success(
                    Weather(
                        city = localWeather.city,
                        country = localWeather.country,
                        temperature = localWeather.temperature,
                        humidity = localWeather.humidity.toInt(),
                        windSpeed = localWeather.windSpeed,
                        description = localWeather.description
                    )
                )
            )
        } else {
            emit(Result.failure(Exception("No local data or data is stale for city: $city")))
        }

        // API'den veri çek ve güncelle
        refreshWeather(city).onSuccess { updatedWeather ->
            emit(Result.success(updatedWeather))
        }.onFailure { error ->
            emit(Result.failure(error))
        }
    }

    suspend fun getForecast(city: String, dayCount: Int): Flow<Result<Forecast>> = flow {
        // Yerel veritabanından tahminleri al (IO işlemi)
        val localForecasts = withContext(Dispatchers.IO) {
            forecastQueries.selectByCity(city).executeAsList()
        }
        if (localForecasts.isNotEmpty() && isDataFresh(localForecasts[0].lastUpdated)) {
            val forecastItems = localForecasts.map {
                ForecastItem(
                    date = it.date,
                    temperature = it.temperature,
                    humidity = it.humidity.toInt(),
                    windSpeed = it.windSpeed,
                    description = it.description
                )
            }
            emit(
                Result.success(
                    Forecast(
                        city = localForecasts[0].city,
                        country = localForecasts[0].country,
                        forecastList = forecastItems
                    )
                )
            )
        } else {
            emit(Result.failure(Exception("No local forecast data or data is stale for city: $city")))
        }

        // API'den veri çek ve güncelle
        refreshForecast(city, dayCount).onSuccess { updatedForecast ->
            emit(Result.success(updatedForecast))
        }.onFailure { error ->
            emit(Result.failure(error))
        }
    }

    private suspend fun refreshWeather(city: String): Result<Weather> = withContext(Dispatchers.IO) {
        try {
            val apiResult = api.getCurrentWeather(city).map { it.toDomain() }
            if (apiResult.isSuccess) {
                val weather = apiResult.getOrThrow()
                weatherQueries.insertOrUpdate(
                    city = weather.city,
                    country = weather.country,
                    temperature = weather.temperature,
                    humidity = weather.humidity.toLong(),
                    windSpeed = weather.windSpeed,
                    description = weather.description,
                    lastUpdated = Clock.System.now().epochSeconds
                )
                // Insert başarılı, en güncel veriyi çek
                val updatedWeather = weatherQueries.selectByCity(city).executeAsOneOrNull()
                if (updatedWeather == null) {
                    Result.failure(Exception("Failed to fetch updated weather data"))
                } else {
                    Result.success(
                        Weather(
                            city = updatedWeather.city,
                            country = updatedWeather.country,
                            temperature = updatedWeather.temperature,
                            humidity = updatedWeather.humidity.toInt(),
                            windSpeed = updatedWeather.windSpeed,
                            description = updatedWeather.description
                        )
                    )
                }
            } else {
                Result.failure(apiResult.exceptionOrNull() ?: Exception("Unknown API error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun refreshForecast(city: String, dayCount: Int): Result<Forecast> = withContext(Dispatchers.IO) {
        try {
            val apiResult = api.getForecast(city, dayCount).map { it.toDomain() }
            if (apiResult.isSuccess) {
                val forecast = apiResult.getOrThrow()
                forecastQueries.transaction {
                    forecastQueries.deleteByCity(city)
                    forecast.forecastList.forEach { item ->
                        forecastQueries.insertOrUpdate(
                            city = forecast.city,
                            country = forecast.country,
                            date = item.date,
                            temperature = item.temperature,
                            humidity = item.humidity.toLong(),
                            windSpeed = item.windSpeed,
                            description = item.description,
                            lastUpdated = Clock.System.now().epochSeconds
                        )
                    }
                }
                // Insert başarılı, en güncel veriyi çek
                val updatedForecasts = forecastQueries.selectByCity(city).executeAsList()
                if (updatedForecasts.isEmpty()) {
                    Result.failure(Exception("Failed to fetch updated forecast data"))
                } else {
                    val forecastItems = updatedForecasts.map {
                        ForecastItem(
                            date = it.date,
                            temperature = it.temperature,
                            humidity = it.humidity.toInt(),
                            windSpeed = it.windSpeed,
                            description = it.description
                        )
                    }
                    Result.success(
                        Forecast(
                            city = updatedForecasts[0].city,
                            country = updatedForecasts[0].country,
                            forecastList = forecastItems
                        )
                    )
                }
            } else {
                Result.failure(apiResult.exceptionOrNull() ?: Exception("Unknown API error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun isDataFresh(lastUpdated: Long): Boolean {
        val oneHourInSeconds = 3600
        return (Clock.System.now().epochSeconds - lastUpdated) < oneHourInSeconds
    }
}