package com.codekan.weathers.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.ImeAction
import com.codekan.weathers.icons.SearchIcon

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onVoiceInput: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text("City, Centrum, Province, Airport...", textAlign = TextAlign.Start, color = Color.White.copy(0.8f)) },
        label = { Text("Search City", textAlign = TextAlign.Start, color = Color.White) },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(12.dp),
        keyboardActions = KeyboardActions {
            if(value.isEmpty().not()) onSearch(value)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Search),
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = { if(value.isEmpty().not()) onSearch(value) }) {
                SearchIcon()
            }
        }
    )
}