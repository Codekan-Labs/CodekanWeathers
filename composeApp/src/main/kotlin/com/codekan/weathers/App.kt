package com.codekan.weathers


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codekan.weathers.screen.ForecastScreen
import com.codekan.weathers.screen.WeatherScreen

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.Weather.route,
    ) {
        composable(Screens.Weather.route) {
            WeatherScreen(navController = navController)
        }
        composable(Screens.Forecast.route) {
            ForecastScreen(navController = navController)
        }
    }
}

sealed class Screens(val route: String) {
    object Weather : Screens("weather")
    object Forecast : Screens("forecast")
}