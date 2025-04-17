package com.codekan.weathers.presentation

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
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual class WeatherViewModel : KoinComponent {
    private val getWeatherUseCase: GetWeatherUseCase by inject()
    private val getForecastUseCase: GetForecastUseCase by inject()

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var weatherJob: Job? = null
    private var forecastJob: Job? = null

    private val _weather = MutableStateFlow<DataState<Weather>>(DataState.Loading)
    actual val weather: StateFlow<DataState<Weather>> = _weather.asStateFlow()

    private val _forecast = MutableStateFlow<DataState<Forecast>>(DataState.Loading)
    actual val forecast: StateFlow<DataState<Forecast>> = _forecast.asStateFlow()

    actual fun getWeather(city: String) {
        scope.launch {
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
       scope.launch {
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