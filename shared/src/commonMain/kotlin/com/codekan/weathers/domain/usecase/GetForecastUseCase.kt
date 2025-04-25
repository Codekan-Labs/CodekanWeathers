package com.codekan.weathers.domain.usecase

import com.codekan.weathers.data.WeatherRepository
import com.codekan.weathers.data.api.DataState
import com.codekan.weathers.domain.model.Forecast
import kotlinx.coroutines.flow.Flow

class GetForecastUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(city: String, dayCount: Int): Flow<DataState<Forecast>> {
        return repository.getForecast(city, dayCount)
    }
}