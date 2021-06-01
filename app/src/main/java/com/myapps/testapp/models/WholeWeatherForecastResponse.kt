package com.myapps.testapp.models

data class WholeWeatherForecastResponse(
    val current:Temp,
    val hourly: List<hourlyTempModel>,
    val daily: List<dailyTempModel>
) {
    data class hourlyTempModel(
        val dt: Long,
        val temp: Float,
        val weather: List<WeatherIcon>
    ) {}

    data class dailyTempModel(
        val dt: Long,
        val temp: DailyTempTime,
        val weather: List<WeatherIcon>
    ) {

        data class DailyTempTime(
            val day: Float,
            val min: Float,
            val max: Float,
            val night: Float,
            val eve: Float,
            val morn: Float
        )
    }

    data class Temp(
        val dt:Long,
        val temp: Float,
        val weather: List<WeatherIcon>
    )

    data class WeatherIcon(
        val icon: String
    )
}