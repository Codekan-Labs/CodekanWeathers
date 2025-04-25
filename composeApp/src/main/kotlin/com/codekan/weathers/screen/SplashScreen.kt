package com.codekan.weathers.screen


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.codekan.weathers.R
import com.codekan.weathers.Screens
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SplashScreen(
    navController: NavHostController
) {
    var isVisible by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val permissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    var city by remember { mutableStateOf("Istanbul") }
    val context = LocalContext.current
    // Konum alma
    LaunchedEffect(Unit) {
        scope.launch {
            delay(2000) // Splash animasyonu için 2 saniye bekle
            if (permissionState.status.isGranted) {
                // Konum al

                val locationClient = LocationServices.getFusedLocationProviderClient(
                    context
                )
                try {
                    locationClient.lastLocation.addOnSuccessListener { location ->
                        if (location != null) {
                            // Gerçek uygulamada: Reverse geocoding ile şehir al
                            city = "CurrentLocation" // Örnek, gerçek konumdan şehir çıkar
                        }
                        isVisible = false
                        navController.navigate(Screens.Weather.route)
                    }
                } catch (e: Exception) {
                    isVisible = false
                    navController.navigate(Screens.Weather.route)
                }
            } else {
                permissionState.launchPermissionRequest()
            }
        }
    }

    // İzin durumu değiştiğinde
    LaunchedEffect(permissionState.status) {
        if (permissionState.status.isGranted) {
            // Konum al (yukarıdaki mantık tekrarlanabilir)
            isVisible = false
            navController.navigate(Screens.Weather.route)
        } else if (!permissionState.status.isGranted && !isVisible) {
            isVisible = false
            navController.navigate(Screens.Weather.route)
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_weather_logo), // Uygulama logosu
                    contentDescription = "App Logo",
                    modifier = Modifier.size(100.dp)
                )
                Text(
                    text = "Weathers",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center
                )
                if (!permissionState.status.isGranted) {
                    Button(
                        onClick = { permissionState.launchPermissionRequest() }
                    ) {
                        Text("Allow Location Access")
                    }
                }
            }
        }
    }
}