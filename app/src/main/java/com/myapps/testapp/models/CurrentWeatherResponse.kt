package com.myapps.testapp.models

data class CurrentWeatherResponse(
    val coord:latLong
) {
    data class latLong(
        val lon:Double,
        val lat:Double
    )
}