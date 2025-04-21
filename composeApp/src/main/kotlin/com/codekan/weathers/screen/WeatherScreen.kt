package com.codekan.weathers.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codekan.weathers.component.ForecastList
import com.codekan.weathers.component.WeatherCard
import com.codekan.weathers.presentation.DataState
import com.codekan.weathers.presentation.WeatherViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WeatherScreen(navController: NavController,viewModel: WeatherViewModel = koinViewModel()) {
    var city by remember { mutableStateOf("Istanbul") }
    var cityInput by remember { mutableStateOf("") }

    val weatherState by viewModel.weather.collectAsState()
    val forecastState by viewModel.forecast.collectAsState()

    LaunchedEffect(city) {
        viewModel.getWeather(city)
        viewModel.getForecast(city, dayCount = 5)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Arama Çubuğu
        OutlinedTextField(
            value = cityInput,
            onValueChange = { cityInput = it },
            label = { Text("Şehir Ara") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            trailingIcon = {
                Button(
                    onClick = {
                        if (cityInput.isNotBlank()) {
                            city = cityInput
                        }
                    },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Ara")
                }
            }
        )

        // Hava Durumu Kartı
        when (weatherState) {
            is DataState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is DataState.Success -> {
                val data = (weatherState as DataState.Success).data
                WeatherCard(data = data, city = city)
            }
            is DataState.Error -> {
                Text(
                    text = "Hata: ${(weatherState as DataState.Error).message}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        // 5 Günlük Tahmin Listesi
        when (forecastState) {
            is DataState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is DataState.Success -> {
                val data = (forecastState as DataState.Success).data
                ForecastList(data = data)
            }
            is DataState.Error -> {
                Text(
                    text = "Hata: ${(forecastState as DataState.Error).message}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}