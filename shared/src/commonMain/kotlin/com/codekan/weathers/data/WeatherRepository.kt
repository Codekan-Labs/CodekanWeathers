package com.codekan.weathers.data

import com.codekan.weathers.admirals.database.DatabaseDriverFactory
import com.codekan.weathers.data.api.DataState
import com.codekan.weathers.data.api.ErrorType
import com.codekan.weathers.data.api.WeatherApi
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

    suspend fun getWeather(city: String): Flow<DataState<Weather>> = flow {
        // Yerel veritabanından veri al (IO işlemi)
        val localWeather = withContext(Dispatchers.IO) {
            weatherQueries.selectByCity(city).executeAsOneOrNull()
        }
        if (localWeather != null && isDataFresh(localWeather.lastUpdated)) {
            emit(
                DataState.Success(
                    Weather(
                        city = localWeather.city,
                        country = localWeather.country,
                        temperature = localWeather.temperature,
                        humidity = localWeather.humidity,
                        windSpeed = localWeather.windSpeed,
                        conditionDescription = localWeather.conditionDescription,
                        feelsLike = localWeather.feelsLike,
                        rainMmHour = localWeather.rainMmHour,
                        sunriseTime = localWeather.sunriseTime,
                        sunsetTime = localWeather.sunsetTime,
                        timeZone = localWeather.timeZone.toInt(),
                        iconCode = localWeather.iconCode,
                    )
                )
            )
        } else {
            emit(refreshWeather(city))
        }
    }

    private suspend fun refreshWeather(city: String): DataState<Weather> = withContext(Dispatchers.IO) {
        try {
            val apiResult = api.getCurrentWeather(city)
            when (apiResult){
                is DataState.Success -> {
                    weatherQueries.insertOrUpdate(
                        city = apiResult.data.city,
                        country = apiResult.data.country,
                        temperature = apiResult.data.temperature,
                        humidity = apiResult.data.humidity,
                        windSpeed = apiResult.data.windSpeed,
                        conditionDescription = apiResult.data.conditionDescription,
                        feelsLike = apiResult.data.feelsLike,
                        rainMmHour = apiResult.data.rainMmHour,
                        sunriseTime = apiResult.data.sunriseTime,
                        sunsetTime = apiResult.data.sunsetTime,
                        timeZone = apiResult.data.timeZone.toLong(),
                        iconCode = apiResult.data.iconCode,
                        lastUpdated = Clock.System.now().epochSeconds
                    )
                    apiResult
                }
                else -> {
                    apiResult
                }
            }
        } catch (e: Exception) {
            DataState.Error(ErrorType.Unknown(e.message ?: "Unknown error"))
        }
    }


    suspend fun getForecast(city: String, dayCount: Int): Flow<DataState<Forecast>> = flow {
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
                DataState.Success(
                    Forecast(
                        city = localForecasts[0].city,
                        country = localForecasts[0].country,
                        forecastList = forecastItems
                    )
                )
            )
        } else {
            emit(refreshForecast(city,dayCount))
        }


    }


    private suspend fun refreshForecast(city: String, dayCount: Int): DataState<Forecast> = withContext(Dispatchers.IO) {
        try {
            val apiResult = api.getForecast(city, dayCount)
            when(apiResult){
                is DataState.Success -> {
                    forecastQueries.transaction {
                        apiResult.data.forecastList.forEach { item ->
                            forecastQueries.insertOrUpdate(
                                city = apiResult.data.city,
                                country = apiResult.data.country,
                                date = item.date,
                                temperature = item.temperature,
                                humidity = item.humidity.toLong(),
                                windSpeed = item.windSpeed,
                                description = item.description,
                                lastUpdated = Clock.System.now().epochSeconds
                            )
                        }

                }
                    apiResult
                }
                else -> {
                    apiResult
                }
            }
        } catch (e: Exception) {
            DataState.Error(ErrorType.Unknown(e.message ?: "Unknown error"))
        }
    }

    suspend fun getRecentWeathers(city: String): Flow<DataState<MutableList<Weather>>> = flow {
        val recentWeathers = withContext(Dispatchers.IO) {
            weatherQueries.selectAll().executeAsList().toMutableList()
        }
        if (recentWeathers.isNotEmpty()) {
            emit(DataState.Success(recentWeathers.map {
                Weather(
                    city = it.city,
                    country = it.country,
                    temperature = it.temperature,
                    humidity = it.humidity,
                    windSpeed = it.windSpeed,
                    conditionDescription = it.conditionDescription,
                    feelsLike = it.feelsLike,
                    rainMmHour = it.rainMmHour,
                    sunriseTime = it.sunriseTime,
                    sunsetTime = it.sunsetTime,
                    timeZone = it.timeZone.toInt(),
                    iconCode = it.iconCode
                )
            }.filter { it.city != city }.toMutableList()))
        } else {
            emit(DataState.Success(mutableListOf()))
        }
    }

    suspend fun getGeoLocation(latitude: Double, longitude: Double): Flow<String> = flow {
        try {
            val apiResult = api.getGeoLocation(latitude, longitude)
            when (apiResult) {
                is DataState.Success -> {
                    emit(apiResult.data)
                }
                else -> {
                    emit("Istanbul")
                }
            }
        } catch (e: Exception) {
            emit("Istanbul")
        }
    }

    private fun isDataFresh(lastUpdated: Long): Boolean {
        val oneHourInSeconds = 3600
        return (Clock.System.now().epochSeconds - lastUpdated) < oneHourInSeconds
    }
}