package com.codekan.weathers.presentation

import com.codekan.weathers.domain.model.Forecast
import com.codekan.weathers.domain.model.Weather
import kotlinx.coroutines.flow.StateFlow

expect class WeatherViewModel {
    val weather: StateFlow<DataState<Weather>>
    val forecast: StateFlow<DataState<Forecast>>
    fun getWeather(city: String)
    fun getForecast(city: String, dayCount: Int)
}