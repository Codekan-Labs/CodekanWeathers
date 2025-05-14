package com.codekan.weathers.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.codekan.weathers.domain.model.ForecastItem
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun ForecastItem(item: ForecastItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.date,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = item.temperature,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = item.humidity,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = item.windSpeed,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}