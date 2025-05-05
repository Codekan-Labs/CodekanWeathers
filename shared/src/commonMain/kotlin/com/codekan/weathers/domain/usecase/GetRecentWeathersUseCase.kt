package com.codekan.weathers.domain.usecase

import com.codekan.weathers.data.WeatherRepository
import com.codekan.weathers.data.api.DataState
import com.codekan.weathers.domain.model.Weather
import kotlinx.coroutines.flow.Flow

class GetRecentWeathersUseCase (
    private val repository: WeatherRepository
){
    suspend operator fun invoke(city : String): Flow<DataState<MutableList<Weather>>> {
        return repository.getRecentWeathers(city)
    }
}