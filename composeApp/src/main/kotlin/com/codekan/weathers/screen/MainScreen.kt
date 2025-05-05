package com.codekan.weathers.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codekan.weathers.component.RecentWeathersList
import com.codekan.weathers.component.SearchBar
import com.codekan.weathers.component.WeatherCard
import com.codekan.weathers.presentation.WeatherViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(navController: NavController, viewModel: WeatherViewModel = koinViewModel()) {
    var cityInput by remember { mutableStateOf("") }

    val weatherState by viewModel.weather.collectAsState()
    val recentWeathersState by viewModel.recentWeathers.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Arama Çubuğu
        SearchBar(
            value = cityInput,
            onValueChange = { cityInput = it },
            onSearch = { newCity ->
                viewModel.updateCity(newCity)
                cityInput = "" // Arama sonrası input’u temizle
            },
            onVoiceInput = {

            }
        )

        // Mevcut konum kartı
        WeatherCard(
            state = weatherState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Recent cities listesi
        RecentWeathersList(state = recentWeathersState, onClick = { weather ->
            viewModel.updateCity(weather.city)
        })
    }
}