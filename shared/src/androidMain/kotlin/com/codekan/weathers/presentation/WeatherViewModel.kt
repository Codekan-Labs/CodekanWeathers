package com.codekan.weathers.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codekan.weathers.domain.model.Forecast
import com.codekan.weathers.domain.model.Weather
import com.codekan.weathers.domain.usecase.GetForecastUseCase
import com.codekan.weathers.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

actual class WeatherViewModel actual constructor(
    val getWeatherUseCase: GetWeatherUseCase,
    val getForecastUseCase: GetForecastUseCase
) : ViewModel() {
    private val _weather = MutableStateFlow<DataState<Weather>>(DataState.Loading)
    actual val weather: StateFlow<DataState<Weather>> = _weather.asStateFlow()

    private val _forecast = MutableStateFlow<DataState<Forecast>>(DataState.Loading)
    actual val forecast: StateFlow<DataState<Forecast>> = _forecast.asStateFlow()

    actual fun getWeather(city: String) {
        viewModelScope.launch {
            getWeatherUseCase(city).collect { result ->
                result
                    .onSuccess { weather ->
                        _weather.value = DataState.Success(weather)
                    }
                    .onFailure { error ->
                        _weather.value = DataState.Error(ErrorType.NetworkError)
                    }
            }
        }
    }

    actual fun getForecast(city: String, dayCount: Int) {
        viewModelScope.launch {
            getForecastUseCase(city, dayCount).collect { result ->
                result
                    .onSuccess { forecast ->
                        _forecast.value = DataState.Success(forecast)
                    }
                    .onFailure { error ->
                        _forecast.value = DataState.Error(ErrorType.NetworkError)
                    }
            }
        }
    }
}