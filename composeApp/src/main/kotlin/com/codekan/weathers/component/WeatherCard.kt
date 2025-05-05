package com.codekan.weathers.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Water
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.codekan.weathers.data.api.DataState
import com.codekan.weathers.domain.model.Weather
import com.codekan.weathers.icons.WeatherIcon

@Composable
fun WeatherCard(
    state: DataState<Weather>,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + scaleIn(initialScale = 0.95f),
        exit = fadeOut() + scaleOut(targetScale = 0.95f)
    ) {
        OutlinedCard(
            modifier = modifier
                .height(220.dp)
                .fillMaxWidth(),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f)),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF1E3A8A).copy(alpha = 0.5f), // Koyu mavi
                                Color(0xFF60A5FA).copy(alpha = 0.3f)  // Açık mavi
                            )
                        )
                    )
                    .padding(16.dp)
            ) {
                when (state) {
                    is DataState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.Center),
                            strokeWidth = 3.dp,
                            color = Color.White
                        )
                    }
                    is DataState.Success -> {
                        val weather = state.data
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Üst: Şehir, sıcaklık, hissedilen ve WeatherIcon
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(horizontalAlignment = Alignment.Start) {
                                    Text(
                                        text = weather.cityCountry,
                                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                        color = Color.White,
                                        textAlign = TextAlign.Start
                                    )
                                    Text(
                                        text = weather.temperature,
                                        style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold),
                                        color = Color.White,
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                    Text(
                                        text = "Felt: ${weather.feelsLike}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White.copy(alpha = 0.7f),
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }
                                WeatherIcon(
                                    iconCode = weather.iconCode
                                )
                            }
                            // Orta: Açıklama
                            Text(
                                text = weather.conditionDescription,
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                maxLines = 2,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )
                            // Alt: Ek veriler (2x2 grid)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Gün doğumu/batımı
                                Column(horizontalAlignment = Alignment.Start) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Outlined.WbSunny,
                                            contentDescription = "Sunrise",
                                            tint = Color(0xFFFBBF24), // Sarı, güneşli his
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Text(
                                            text = weather.sunriseTime,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.White,
                                            modifier = Modifier.padding(start = 6.dp)
                                        )
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(top = 6.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.WbSunny,
                                            contentDescription = "Sunset",
                                            tint = Color(0xFFF97316), // Turuncu, gün batımı
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Text(
                                            text = weather.sunsetTime,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.White,
                                            modifier = Modifier.padding(start = 6.dp)
                                        )
                                    }
                                }
                                // Rüzgar ve nem
                                Column(horizontalAlignment = Alignment.End) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Air,
                                            contentDescription = "Wind",
                                            tint = Color(0xFF60A5FA), // Mavi, rüzgar hissi
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Text(
                                            text = weather.windSpeed,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.White,
                                            modifier = Modifier.padding(start = 6.dp)
                                        )
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(top = 6.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Water,
                                            contentDescription = "Humidity",
                                            tint = Color(0xFF22D3EE), // Camgöbeği, nem hissi
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Text(
                                            text = weather.humidity,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.White,
                                            modifier = Modifier.padding(start = 6.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    is DataState.Error -> {
                        Text(
                            text = state.message ?: "Bir hata oluştu",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}