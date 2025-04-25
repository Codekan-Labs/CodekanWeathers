package com.codekan.weathers.presentation

import com.codekan.weathers.data.api.DataState
import com.codekan.weathers.domain.model.Forecast
import com.codekan.weathers.domain.model.Weather
import com.codekan.weathers.domain.usecase.GetForecastUseCase
import com.codekan.weathers.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.flow.StateFlow

expect class WeatherViewModel(
    getWeatherUseCase: GetWeatherUseCase,
    getForecastUseCase: GetForecastUseCase
) {
    val weather: StateFlow<DataState<Weather>>
    val forecast: StateFlow<DataState<Forecast>>
    fun getWeather(city: String)
    fun getForecast(city: String, dayCount: Int)
}