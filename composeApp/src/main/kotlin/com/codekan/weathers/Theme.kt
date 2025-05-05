package com.codekan.weathers

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val LightColorScheme = lightColorScheme(
    primary = Color.Black, // Mavi ton, butonlar için
    secondary = Color(0xFF03DAC6), // Aksan rengi
    background = Color(0xFF121212), // Koyu arka plan, GIF’le uyumlu
    surface = Color(0xFF1E1E1E), // Kartlar için koyu gri
    onPrimary = Color.White, // Primary üstünde beyaz metin
    onSecondary = Color.White, // Secondary üstünde beyaz metin
    onBackground = Color.White, // Background üstünde beyaz metin
    onSurface = Color.White, // Surface üstünde beyaz metin
    outline = Color.White // Kenarlıklar için açık gri
)

@Composable
fun WeathersTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

val buttonColors = ButtonColors(
    containerColor = Color.Transparent,
    contentColor = Color.White,
    disabledContainerColor = Color.Transparent,
    disabledContentColor = Color.White
)