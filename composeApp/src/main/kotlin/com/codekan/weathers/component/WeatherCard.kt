package com.codekan.weathers.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WeatherCard(data: com.codekan.weathers.domain.model.Weather, city: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "$city Hava Durumu",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Sıcaklık: ${data.temperature}°C",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Hissedilen: ${/*data.feelsLike?.toString() ?:*/ "Bilinmiyor"}°C",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Açıklama: ${data.description}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Nem: ${data.humidity}%",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Rüzgar Hızı: ${data.windSpeed} m/s",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Basınç: ${/*data.pressure?.toString() ?:*/ "Bilinmiyor"} hPa",
                style = MaterialTheme.typography.bodyLarge
            )
            val sunrise = /*data.sunrise?.let {
                Instant.ofEpochSecond(it)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
                    .format(DateTimeFormatter.ofPattern("HH:mm"))
            } ?:*/ "Bilinmiyor"
            val sunset = /*data.sunset?.let {
                Instant.ofEpochSecond(it)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
                    .format(DateTimeFormatter.ofPattern("HH:mm"))
            } ?: */"Bilinmiyor"
            Text(
                text = "Gün Doğumu: $sunrise",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Gün Batımı: $sunset",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}