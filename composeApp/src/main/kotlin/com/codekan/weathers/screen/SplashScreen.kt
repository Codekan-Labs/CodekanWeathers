package com.codekan.weathers.screen


import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
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
import com.codekan.weathers.presentation.WeatherViewModel
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


@SuppressLint("MissingPermission")
@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: WeatherViewModel = koinViewModel()
) {
    val isVisible by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    var permissionRequested by remember { mutableStateOf(false) }
    // Konum izni talebi
    val locationProvider = LocationServices.getFusedLocationProviderClient(LocalContext.current)
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        scope.launch {
            if (isGranted) {
                locationProvider.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        // Konum alındı, güncelle
                        viewModel.getGeoLocation(location.latitude, location.longitude)
                    }
                }.addOnFailureListener {
                    // Konum alınamadı, hata işleme
                    viewModel.getGeoLocation(41.01384, 28.94966) // Varsayılan değer
                }

            }
            permissionRequested = true
            navController.navigate(Screens.Main.route)
        }
    }

    // Splash animasyonu ve izin talebi
    LaunchedEffect(Unit) {
        delay(1000) // 1 saniye splash animasyonu
        permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
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
            }
        }
    }
}