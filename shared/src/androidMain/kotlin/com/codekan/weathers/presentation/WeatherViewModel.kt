package com.codekan.weathers.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codekan.weathers.data.api.DataState
import com.codekan.weathers.domain.model.Forecast
import com.codekan.weathers.domain.model.Weather
import com.codekan.weathers.domain.usecase.GetForecastUseCase
import com.codekan.weathers.domain.usecase.GetGeoLocationUseCase
import com.codekan.weathers.domain.usecase.GetRecentWeathersUseCase
import com.codekan.weathers.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

actual class WeatherViewModel actual constructor(
    val getWeatherUseCase: GetWeatherUseCase,
    val getForecastUseCase: GetForecastUseCase,
    val getRecentWeathersUseCase: GetRecentWeathersUseCase,
    val getGeoLocationUseCase: GetGeoLocationUseCase
) : ViewModel() {
    private val _weather = MutableStateFlow<DataState<Weather>>(DataState.Loading)
    actual val weather: StateFlow<DataState<Weather>> = _weather.asStateFlow()

    private val _recentWeathers = MutableStateFlow<DataState<MutableList<Weather>>>(DataState.Loading)
    actual val recentWeathers: StateFlow<DataState<MutableList<Weather>>> = _recentWeathers.asStateFlow()

    private val _forecast = MutableStateFlow<DataState<Forecast>>(DataState.Loading)
    actual val forecast: StateFlow<DataState<Forecast>> = _forecast.asStateFlow()

    private val _currentCity = MutableStateFlow("Istanbul")
    actual val currentCity: StateFlow<String> = _currentCity.asStateFlow()

    init {
        viewModelScope.launch {
            currentCity.collectLatest { city ->
                getWeather()
                getRecentWeathers()
                //getForecast(7)
            }
        }

    }
    actual fun getWeather() {
        viewModelScope.launch {
            getWeatherUseCase(currentCity.value).collect { result ->
                _weather.value = result
            }
        }
    }

    actual fun getForecast(dayCount: Int) {
        viewModelScope.launch {
            getForecastUseCase(currentCity.value, dayCount).collect { result ->
                _forecast.value = result
            }
        }
    }

    actual fun getRecentWeathers() {
        viewModelScope.launch {
            getRecentWeathersUseCase(currentCity.value).collect { result ->
                _recentWeathers.value = result
            }
        }
    }

    actual fun updateCity(city: String) {
        viewModelScope.launch {
            _currentCity.value = city
        }

    }

    actual fun getGeoLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            getGeoLocationUseCase(latitude, longitude).collect { result ->
                _currentCity.value = result
            }
        }
    }
}