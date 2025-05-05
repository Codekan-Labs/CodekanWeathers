package com.codekan.weathers.presentation

import com.codekan.weathers.data.api.DataState
import com.codekan.weathers.domain.model.Forecast
import com.codekan.weathers.domain.model.Weather
import com.codekan.weathers.domain.usecase.GetForecastUseCase
import com.codekan.weathers.domain.usecase.GetGeoLocationUseCase
import com.codekan.weathers.domain.usecase.GetRecentWeathersUseCase
import com.codekan.weathers.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.flow.StateFlow

expect class WeatherViewModel(
    getWeatherUseCase: GetWeatherUseCase,
    getForecastUseCase: GetForecastUseCase,
    getRecentWeathersUseCase: GetRecentWeathersUseCase,
    getGeoLocationUseCase: GetGeoLocationUseCase
) {
    val weather: StateFlow<DataState<Weather>>
    val recentWeathers: StateFlow<DataState<MutableList<Weather>>>
    val forecast: StateFlow<DataState<Forecast>>
    val currentCity: StateFlow<String>
    fun getWeather()
    fun getForecast(dayCount: Int)
    fun getRecentWeathers()
    fun updateCity(city: String)
    fun getGeoLocation(latitude: Double, longitude: Double)
}