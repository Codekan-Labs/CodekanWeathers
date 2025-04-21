package com.codekan.weathers

import com.codekan.weathers.data.WeatherRepository
import com.codekan.weathers.data.api.WeatherApi
import com.codekan.weathers.domain.usecase.GetForecastUseCase
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
    }

    // Presentation katmanı bağımlılıkları
    private val presentationModule: Module = module {
        factory { WeatherViewModel(get(),get()) } // Geçici olarak, constructor injection sonrası güncellenecek
    }

    fun getWeatherViewModel(): WeatherViewModel = getKoin().get()

}

