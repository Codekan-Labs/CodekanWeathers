package com.codekan.weathers.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codekan.weathers.component.ForecastList
import com.codekan.weathers.component.WeatherCard
import com.codekan.weathers.data.api.DataState
import com.codekan.weathers.presentation.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CityDetailScreen(navController: NavController) {
    val viewModel: WeatherViewModel = koinViewModel()
    val weatherState by viewModel.weather.collectAsState()
    val forecastState by viewModel.forecast.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getForecast(5) // 5 günlük tahmin için 40 zaman damgası
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        WeatherCard(
            state = weatherState,
            modifier = Modifier.fillMaxWidth(),
            navController = null
        )
        when (forecastState) {
            is DataState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is DataState.Success -> {
                val state = forecastState.data
                ForecastList(data = forecastState.data)
            }
            is DataState.Error -> {
                Text(
                    text = "Error loading forecast: ${(forecastState as DataState.Error).message}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}