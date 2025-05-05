package com.codekan.weathers.data.util

/*enum class WeatherCondition(val id: Int, val description: String, val group: String, val iconCode: String) {
    // Thunderstorm
    THUNDERSTORM_WITH_LIGHT_RAIN(200, "thunderstorm with light rain", "Thunderstorm","11"),
    THUNDERSTORM_WITH_RAIN(201, "thunderstorm with rain", "Thunderstorm","11"),
    THUNDERSTORM_WITH_HEAVY_RAIN(202, "thunderstorm with heavy rain", "Thunderstorm","11"),
    LIGHT_THUNDERSTORM(210, "light thunderstorm", "Thunderstorm","11"),
    THUNDERSTORM(211, "thunderstorm", "Thunderstorm","11"),
    HEAVY_THUNDERSTORM(212, "heavy thunderstorm", "Thunderstorm","11"),
    RAGGED_THUNDERSTORM(221, "ragged thunderstorm", "Thunderstorm","11"),
    THUNDERSTORM_WITH_LIGHT_DRIZZLE(230, "thunderstorm with light drizzle", "Thunderstorm","11"),
    THUNDERSTORM_WITH_DRIZZLE(231, "thunderstorm with drizzle", "Thunderstorm","11"),
    THUNDERSTORM_WITH_HEAVY_DRIZZLE(232, "thunderstorm with heavy drizzle", "Thunderstorm","11"),

    // Drizzle
    LIGHT_INTENSITY_DRIZZLE(300, "light intensity drizzle", "Drizzle","09"),
    DRIZZLE(301, "drizzle", "Drizzle","09"),
    HEAVY_INTENSITY_DRIZZLE(302, "heavy intensity drizzle", "Drizzle","09"),
    LIGHT_INTENSITY_DRIZZLE_RAIN(310, "light intensity drizzle rain", "Drizzle","09"),
    DRIZZLE_RAIN(311, "drizzle rain", "Drizzle","09"),
    HEAVY_INTENSITY_DRIZZLE_RAIN(312, "heavy intensity drizzle rain", "Drizzle","09"),
    SHOWER_RAIN_AND_DRIZZLE(313, "shower rain and drizzle", "Drizzle","09"),
    HEAVY_SHOWER_RAIN_AND_DRIZZLE(314, "heavy shower rain and drizzle", "Drizzle","09"),
    SHOWER_DRIZZLE(321, "shower drizzle", "Drizzle","09"),

    // Rain
    LIGHT_RAIN(500, "light rain", "Rain","10"),
    MODERATE_RAIN(501, "moderate rain", "Rain","10"),
    HEAVY_INTENSITY_RAIN(502, "heavy intensity rain", "Rain","10"),
    VERY_HEAVY_RAIN(503, "very heavy rain", "Rain","10"),
    EXTREME_RAIN(504, "extreme rain", "Rain","10"),
    FREEZING_RAIN(511, "freezing rain", "Rain","10"),
    LIGHT_INTENSITY_SHOWER_RAIN(520, "light intensity shower rain", "Rain","10"),
    SHOWER_RAIN(521, "shower rain", "Rain","10"),
    HEAVY_INTENSITY_SHOWER_RAIN(522, "heavy intensity shower rain", "Rain","10"),
    RAGGED_SHOWER_RAIN(531, "ragged shower rain", "Rain","10"),

    // Snow
    LIGHT_SNOW(600, "light snow", "Snow","13"),
    SNOW(601, "snow", "Snow","13"),
    HEAVY_SNOW(602, "heavy snow", "Snow","13"),
    SLEET(611, "sleet", "Snow","13"),
    LIGHT_SHOWER_SLEET(612, "light shower sleet", "Snow","13"),
    SHOWER_SLEET(613, "shower sleet", "Snow","13"),
    LIGHT_RAIN_AND_SNOW(615, "light rain and snow", "Snow","13"),
    RAIN_AND_SNOW(616, "rain and snow", "Snow","13"),
    LIGHT_SHOWER_SNOW(620, "light shower snow", "Snow","13"),
    SHOWER_SNOW(621, "shower snow", "Snow","13"),
    HEAVY_SHOWER_SNOW(622, "heavy shower snow", "Snow","13"),

    // Atmosphere
    MIST(701, "mist", "Atmosphere","50"),
    SMOKE(711, "smoke", "Atmosphere","50"),
    HAZE(721, "haze", "Atmosphere","50"),
    SAND_DUST_WHIRLS(731, "sand/dust whirls", "Atmosphere","50"),
    FOG(741, "fog", "Atmosphere","50"),
    SAND(751, "sand", "Atmosphere","50"),
    DUST(761, "dust", "Atmosphere","50"),
    VOLCANIC_ASH(762, "volcanic ash", "Atmosphere","50"),
    SQUALLS(771, "squalls", "Atmosphere","50"),
    TORNADO(781, "tornado", "Atmosphere","50"),

    // Clear
    CLEAR_SKY(800, "clear sky", "Clear","01"),

    // Clouds
    FEW_CLOUDS(801, "few clouds", "Clouds","02"),
    SCATTERED_CLOUDS(802, "scattered clouds", "Clouds","03"),
    BROKEN_CLOUDS(803, "broken clouds", "Clouds","04"),
    OVERCAST_CLOUDS(804, "overcast clouds", "Clouds","04");

    companion object {
        // ID’ye göre WeatherCondition bul
        fun fromId(id: Int?): WeatherCondition =
            entries.find { it.id == id } ?: CLEAR_SKY

        // Description’a göre WeatherCondition bul (case-insensitive)
        fun fromDescription(description: String): WeatherCondition? =
            entries.find { it.description.equals(description, ignoreCase = true) }

    }
}*/