package com.espinas.raman.NetWork.api

import com.myapps.testapp.models.CurrentWeatherResponse
import com.myapps.testapp.models.WholeWeatherForecastResponse
import retrofit2.http.*

interface WeatherApis {

    companion object {
        const val BASE_URL = "https:api.openweathermap.org/data/2.5/"
        private const val API_KEY ="api_key_from_openweather"
    }

    @GET("weather?appid=$API_KEY")
    suspend fun  getLatLong(
        @Query("q") cityName:String
    ):CurrentWeatherResponse

   @GET("onecall?exclude=minutely,alerts&appid=$API_KEY&units=metric")
    suspend fun  getWholeWeatherForecast(
       @Query("lat") lat :Double,
       @Query("lon") lon :Double
    ):WholeWeatherForecastResponse

}
