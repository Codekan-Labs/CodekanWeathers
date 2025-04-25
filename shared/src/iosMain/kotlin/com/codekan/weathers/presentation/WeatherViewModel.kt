package com.codekan.weathers.presentation

import com.codekan.weathers.data.api.DataState
import com.codekan.weathers.domain.model.Forecast
import com.codekan.weathers.domain.model.Weather
import com.codekan.weathers.domain.usecase.GetForecastUseCase
import com.codekan.weathers.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

actual class WeatherViewModel actual constructor(
    val getWeatherUseCase: GetWeatherUseCase,
    val getForecastUseCase: GetForecastUseCase
) {
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var weatherJob: Job? = null
    private var forecastJob: Job? = null

    private val _weather = MutableStateFlow<DataState<Weather>>(DataState.Loading)
    actual val weather: StateFlow<DataState<Weather>> = _weather.asStateFlow()

    private val _forecast = MutableStateFlow<DataState<Forecast>>(DataState.Loading)
    actual val forecast: StateFlow<DataState<Forecast>> = _forecast.asStateFlow()

    actual fun getWeather(city: String) {
        // Önceki weatherJob'ı iptal et
        weatherJob?.cancel()
        weatherJob = scope.launch {
            _weather.value = DataState.Loading
            getWeatherUseCase(city).collect { result ->
                _weather.value = result
            }
        }
    }

    actual fun getForecast(city: String, dayCount: Int) {
        // Önceki forecastJob'ı iptal et
        forecastJob?.cancel()
        forecastJob = scope.launch {
            _forecast.value = DataState.Loading
            getForecastUseCase(city, dayCount).collect { result ->
                _forecast.value = result
            }
        }
    }

    // ViewModel temizliği
    fun clear() {
        scope.cancel()
    }
}