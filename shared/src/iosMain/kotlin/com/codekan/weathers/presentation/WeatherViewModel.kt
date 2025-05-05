package com.codekan.weathers.presentation

import com.codekan.weathers.data.api.DataState
import com.codekan.weathers.domain.model.Forecast
import com.codekan.weathers.domain.model.Weather
import com.codekan.weathers.domain.usecase.GetForecastUseCase
import com.codekan.weathers.domain.usecase.GetGeoLocationUseCase
import com.codekan.weathers.domain.usecase.GetRecentWeathersUseCase
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
    val getForecastUseCase: GetForecastUseCase,
    val getRecentWeathersUseCase: GetRecentWeathersUseCase,
    val getGeoLocationUseCase: GetGeoLocationUseCase
) {
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var weatherJob: Job? = null
    private var forecastJob: Job? = null
    private var recentWeathersJob : Job? = null

    private val _weather = MutableStateFlow<DataState<Weather>>(DataState.Loading)
    actual val weather: StateFlow<DataState<Weather>> = _weather.asStateFlow()

    private val _recentWeathers = MutableStateFlow<DataState<MutableList<Weather>>>(DataState.Loading)
    actual val recentWeathers: StateFlow<DataState<MutableList<Weather>>> = _recentWeathers.asStateFlow()

    private val _forecast = MutableStateFlow<DataState<Forecast>>(DataState.Loading)
    actual val forecast: StateFlow<DataState<Forecast>> = _forecast.asStateFlow()


    private val _currentCity = MutableStateFlow("Istanbul")
    actual val currentCity: StateFlow<String> = _currentCity.asStateFlow()


    actual fun getWeather() {
        // Önceki weatherJob'ı iptal et
        weatherJob?.cancel()
        weatherJob = scope.launch {
            _weather.value = DataState.Loading
            getWeatherUseCase(currentCity.value).collect { result ->
                _weather.value = result
            }
        }
    }

    actual fun getForecast(dayCount: Int) {
        // Önceki forecastJob'ı iptal et
        forecastJob?.cancel()
        forecastJob = scope.launch {
            _forecast.value = DataState.Loading
            getForecastUseCase(currentCity.value, dayCount).collect { result ->
                _forecast.value = result
            }
        }
    }

    actual fun getRecentWeathers() {
        recentWeathersJob?.cancel()
        recentWeathersJob = scope.launch {
            _recentWeathers.value = DataState.Loading
            getRecentWeathersUseCase(currentCity.value).collect { result ->
                _recentWeathers.value = result
            }
        }
    }

    actual fun updateCity(city: String) {
            _currentCity.value = city
    }

    actual fun getGeoLocation(latitude: Double, longitude: Double) {
        scope.launch {
            getGeoLocationUseCase(latitude, longitude).collect { result ->
                _currentCity.value = result
            }
        }
    }

    // ViewModel temizliği
    fun clear() {
        scope.cancel()
    }
}