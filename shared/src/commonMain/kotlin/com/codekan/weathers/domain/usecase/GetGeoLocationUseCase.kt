package com.codekan.weathers.domain.usecase

import com.codekan.weathers.data.WeatherRepository
import kotlinx.coroutines.flow.Flow

class GetGeoLocationUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(long : Double, lat : Double) : Flow<String> {
        return repository.getGeoLocation(long, lat)
    }
}