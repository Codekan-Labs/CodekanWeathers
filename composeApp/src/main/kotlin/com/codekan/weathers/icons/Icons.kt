package com.codekan.weathers.icons

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.codekan.weathers.R
import com.codekan.weathers.data.util.getWeatherIconUrl

@Composable
fun SearchIcon() {
    Icon(
        imageVector = Icons.Default.Search,
        contentDescription = "Search Icon",
        tint = Color.White
    )
}

@Composable
fun VoiceInputIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_microphone),
        contentDescription = "Voice Input Icon",
        tint = Color.White,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun CityIcon() {
    Icon(
        imageVector = Icons.Default.Home,
        contentDescription = "City Icon",
        tint = Color.White,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun WeatherIcon(
    iconCode: String
) {
    AsyncImage(
        model = getWeatherIconUrl(iconCode),
        contentDescription = "Weather Icon",
        modifier = Modifier.size(96.dp)
    )
}