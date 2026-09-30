# Codekan Weathers

A weather app project demonstrating native Android UI with Jetpack Compose and a Kotlin Multiplatform data layer. It brings together REST API integration, local SQLite caching, reactive state, and platform-specific services in a small mobile codebase.

**Project status:** Android weather screens are implemented. The shared module targets Android and iOS, but the SwiftUI app is still a starter shell; a complete iOS weather experience is not implemented. This is a portfolio project, with no production release or build verification claimed here.

## Implemented in the Android source

- City search and a list of previously fetched cities.
- Current conditions: temperature, feels-like temperature, humidity, wind, sunrise and sunset.
- A city detail screen with forecast entries, plus loading and error states.
- Location permission handling and city lookup using the last known device location and reverse geocoding. The initial city is Istanbul.
- Day/night GIF backgrounds and weather icons.
- SQLDelight persistence for weather and forecasts, with a one-hour freshness window before fetching again.

## Architecture and stack

The shared layer separates API response models and mapping, a repository, domain models, and use cases. Platform implementations supply HTTP engines, database drivers, API keys, time conversion, and view models. Android screens observe `StateFlow` through Compose.

| Area | Technology |
| --- | --- |
| Shared logic | Kotlin Multiplatform 2.1.10, coroutines, StateFlow |
| Networking | Ktor 3.1.2, kotlinx.serialization, OpenWeather endpoints |
| Local storage | SQLDelight 2.0.2 with Android and Native SQLite drivers |
| Dependency injection | Koin 4.0.4 |
| Android UI | Jetpack Compose, Material 3, Navigation Compose, Coil |
| Android location | Google Play services Fused Location Provider |
| iOS integration | Kotlin/Native framework, SKIE, SwiftUI starter app |

### Repository layout

- [`composeApp/`](composeApp/) — Android application, Compose screens, navigation, resources, and location permission flow. Despite the module name, its UI is Android-only.
- [`shared/`](shared/) — common API, repository, domain and persistence code; Android and iOS implementations.
- [`iosApp/`](iosApp/) — Xcode project and SwiftUI entry point.
- [`gradle/libs.versions.toml`](gradle/libs.versions.toml) — dependency and plugin versions.

## Android setup

### Prerequisites

- Android Studio with support for the pinned Android Gradle Plugin 8.5.2 and Kotlin 2.1.10.
- JDK 17 and Android SDK Platform 35. The app targets SDK 34 and supports Android API 24 or newer.
- The included Gradle 8.9 wrapper; dependency downloads require internet access.
- Your own OpenWeather API key with access to current weather, forecast, and reverse-geocoding endpoints.

### Configure and run

1. Clone the repository and open its root folder in Android Studio.
2. Configure your Android SDK through Android Studio or the ignored `local.properties` file.
3. Add the following to your user-level `~/.gradle/gradle.properties` file, replacing the placeholder with your own key:

   ```properties
   OPEN_WEATHER_API_KEY=YOUR_OPENWEATHER_API_KEY
   ```

   The Android shared module reads this Gradle property into `BuildConfig`. `local.properties` is not read for the API key. Keep credentials out of version control; the resulting client binary contains the key, so this mechanism is not secure server-side secret storage.

4. Sync Gradle, select the `composeApp` run configuration, and launch an emulator or device. Use a device with Google Play services for the location flow; city search is also available.

The Android debug build command, from the repository root, is:

```bash
./gradlew :composeApp:assembleDebug
```

After a successful build, the APK is generated at `composeApp/build/outputs/apk/debug/composeApp-debug.apk`. This command is documented from the module configuration; it has not been executed as part of this README update.

## iOS integration status

The shared module declares `iosX64`, `iosArm64`, and `iosSimulatorArm64` targets and a static `shared` framework. On macOS with Xcode and JDK 17, open [`iosApp/iosApp.xcodeproj`](iosApp/iosApp.xcodeproj/). Its build phase invokes `:shared:embedAndSignAppleFrameworkForXcode`; signing settings are in [`Config.xcconfig`](iosApp/Configuration/Config.xcconfig).

This is an integration starting point, not a ready-to-run weather app. `ContentView.swift` still references the template `Greeting` class, which is absent from the shared source, and imports `Shared` while the configured framework name is `shared`. These need to be reconciled before expecting a successful iOS build. The iOS key provider expects an `OpenWeatherApiKey` entry in the app's Info.plist, but that entry is not configured in the checked-in plist. The Android Gradle key property does not configure iOS.

## Current limitations

- The forecast request passes `5` as OpenWeather's `cnt` parameter. This limits returned forecast entries; it should not be presented as a complete five-day forecast.
- The cache reuses fresh data but does not fall back to stale weather when a refresh fails. Offline operation is therefore limited.
- The microphone action is a placeholder; voice search is not implemented.
- No automated test sources or app screenshots are included. Builds, device behavior, and live API responses have not been verified for this documentation update.

The code illustrates mobile integration patterns relevant to client work: Compose UI, API-to-domain mapping, persisted data, dependency injection, and shared Kotlin logic with native platform boundaries.
