package com.codekan.weathers


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.codekan.weathers.data.util.TimeUtils
import com.codekan.weathers.screen.SplashScreen
import com.codekan.weathers.screen.MainScreen

@Composable
fun App() {

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(if (TimeUtils.isDayTime()) R.drawable.day else R.drawable.night)
            .decoderFactory(GifDecoder.Factory()) // GIF oynatma için
            .build(),
        contentDescription = "Background GIF",
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f)),
        contentScale = ContentScale.Crop
    )
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route,
    ) {
        composable(Screens.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screens.Main.route) {
            MainScreen(navController = navController)
        }
    }
}

sealed class Screens(val route: String) {
    data object Splash : Screens("splash")
    data object Main : Screens("weather")
}