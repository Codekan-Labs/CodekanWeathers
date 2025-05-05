package com.codekan.weathers.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codekan.weathers.data.api.DataState
import com.codekan.weathers.domain.model.Weather

@Composable
fun RecentWeathersList(
    state: DataState<List<Weather>>,
    onClick: (Weather) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
    ) {
        when (state) {
            is DataState.Error -> {
                item {
                    Text(
                        text = "Error: ${state.message ?: "Unknown error"}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
            is DataState.Loading -> {
                item {
                    Text(
                        text = "Loading recent cities...",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
            is DataState.Success -> {
                val recentCities = state.data
                if (recentCities.isEmpty()) {
                    item {
                        Text(
                            text = "No recent cities found",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                } else {
                    items(recentCities) { recentCity ->
                        RecentCityCard(
                            data = recentCity,
                            onClick = { onClick(recentCity) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}