package com.myapps.testapp.ui

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.espinas.raman.NetWork.LoadState
import com.espinas.raman.NetWork.api.WeatherApis
import com.myapps.testapp.models.CurrentWeatherResponse
import com.myapps.testapp.models.WholeWeatherForecastResponse
import com.myapps.testapp.utils.APP_TAG
import dagger.assisted.Assisted
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ActivityViewModel @ViewModelInject constructor(
    private val weatherApis: WeatherApis,
) : ViewModel() {


    private var _LoadingStateLiveData = MutableLiveData<LoadState>()
     var  currentCity =MutableLiveData<String>("Tabriz")
    //get latlang of city
    private val _LatLngLiveData = MutableLiveData<CurrentWeatherResponse>()

    fun getLatLang(cityName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                Log.i(APP_TAG, "getLatLang: $cityName")
                _LoadingStateLiveData.postValue(LoadState.LOADING)
                val result = weatherApis.getLatLong(cityName)
                _LatLngLiveData.postValue(result)
                _LoadingStateLiveData.postValue(LoadState.LOADED)

            } catch (e: Exception) {

                _LoadingStateLiveData.postValue(LoadState.ERROR)
                Log.i(APP_TAG, "getLatLong:$e ")
            }
        }
    }

    val LatLngLiveData: LiveData<CurrentWeatherResponse> = _LatLngLiveData



// get whole weather of city
    private val _WholeWeatherForcastLiveData = MutableLiveData<WholeWeatherForecastResponse>()

    fun getWholeWeatherForecast(
    lat:Double,
    lon:Double
    ){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _LoadingStateLiveData.postValue(LoadState.LOADING)
                val result = weatherApis.getWholeWeatherForecast(lat,lon)
                _WholeWeatherForcastLiveData.postValue(result)
                _LoadingStateLiveData.postValue(LoadState.LOADED)

            } catch (e: Exception) {
                _LoadingStateLiveData.postValue(LoadState.ERROR)
                Log.i(APP_TAG, "getWholeWeatherForecast:$e ")
            }
        }
    }

    val WholeWeatherForcastLiveData :LiveData<WholeWeatherForecastResponse> = _WholeWeatherForcastLiveData


    val LoadStateLiveDate:LiveData<LoadState> = _LoadingStateLiveData
}