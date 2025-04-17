package com.codekan.weathers.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codekan.weathers.domain.model.Forecast
import com.codekan.weathers.domain.model.Weather
import com.codekan.weathers.domain.usecase.GetForecastUseCase
import com.codekan.weathers.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual class WeatherViewModel : ViewModel(), KoinComponent {
    private val getWeatherUseCase: GetWeatherUseCase by inject()
    private val getForecastUseCase: GetForecastUseCase by inject()

    private var weatherJob: Job? = null
    private var forecastJob: Job? = null

    private val _weather = MutableStateFlow<DataState<Weather>>(DataState.Loading)
    actual val weather: StateFlow<DataState<Weather>> = _weather.asStateFlow()

    private val _forecast = MutableStateFlow<DataState<Forecast>>(DataState.Loading)
    actual val forecast: StateFlow<DataState<Forecast>> = _forecast.asStateFlow()

    actual fun getWeather(city: String) {
        viewModelScope.launch {
            _weather.value = DataState.Loading
            getWeatherUseCase(city)
                .onSuccess { weather ->
                    _weather.value = DataState.Success(weather)
                }
                .onFailure { error ->
                    _weather.value = DataState.Error(ErrorType.NetworkError)
                }
        }
    }

    actual fun getForecast(city: String, dayCount: Int) {
        viewModelScope.launch {
            _forecast.value = DataState.Loading
            getForecastUseCase(city,dayCount)
                .onSuccess { forecast ->
                    _forecast.value = DataState.Success(forecast)
                }
                .onFailure { error ->
                    _forecast.value = DataState.Error(ErrorType.NetworkError)
                }
        }
    }

}