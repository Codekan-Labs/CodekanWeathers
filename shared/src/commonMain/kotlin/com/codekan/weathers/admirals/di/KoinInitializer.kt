package com.codekan.weathers.admirals.di

import com.codekan.weathers.admirals.database.DatabaseDriverFactory
import com.codekan.weathers.admirals.ktor.KtorClient
import com.codekan.weathers.data.WeatherRepository
import com.codekan.weathers.data.api.WeatherApi
import com.codekan.weathers.domain.usecase.GetForecastUseCase
import com.codekan.weathers.domain.usecase.GetGeoLocationUseCase
import com.codekan.weathers.domain.usecase.GetRecentWeathersUseCase
import com.codekan.weathers.domain.usecase.GetWeatherUseCase
import com.codekan.weathers.presentation.WeatherViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin

class KoinInitializer(
    private val driverFactory: DatabaseDriverFactory
) {
    fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
        startKoin {
            printLogger() // Hata ayıklama için
            appDeclaration()
            modules(dataModule, domainModule, presentationModule)
        }
    }

    // Veri katmanı bağımlılıkları
    private val dataModule: Module = module {
        single { KtorClient } // KtorClient nesnesi
        single { WeatherApi(get()) } // WeatherApi, apiKey ve KtorClient alıyor
        single { WeatherRepository(get(),driverFactory) } // WeatherRepository, WeatherApi alıyor
    }

    // Domain katmanı bağımlılıkları
    private val domainModule: Module = module {
        factory { GetWeatherUseCase(get()) } // GetWeatherUseCase, WeatherRepository alıyor
        factory { GetForecastUseCase(get()) } // GetForecastUseCase, WeatherRepository alıyor
        factory { GetRecentWeathersUseCase(get()) } // GetForecastUseCase, WeatherRepository alıyor
        factory { GetGeoLocationUseCase(get()) } // GetLongLatUseCase, WeatherRepository alıyor
    }

    // Presentation katmanı bağımlılıkları
    private val presentationModule: Module = module {
        single { WeatherViewModel(get(),get(),get(),get()) } // Geçici olarak, constructor injection sonrası güncellenecek

    }

    fun getWeatherViewModel(): WeatherViewModel = getKoin().get()

}

