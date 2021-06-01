package com.myapps.testapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.espinas.raman.HelperAndUtils.CheckConnectionLiveData
import com.espinas.raman.NetWork.LoadState
import com.espinas.raman.ui.CustomerInsurancePage.DailyRecyclerAdapter
import com.espinas.raman.ui.CustomerInsurancePage.HourlyRecyclerAdapter
import com.espinas.raman.ui.HomePage.adapters.CityRecAdapter
import com.myapps.testapp.R
import com.myapps.testapp.databinding.ActivityMainBinding
import com.myapps.testapp.models.WholeWeatherForecastResponse
import com.myapps.testapp.utils.APP_TAG
import com.myapps.testapp.utils.DateAndTimeUtils
import com.myapps.testapp.utils.SelectImage
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DailyRecyclerAdapter.onUserInsuranceItemClicked,
    CityRecAdapter.onCityNameClickListener {



    private lateinit var binding: ActivityMainBinding
    private val viewModel: ActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val connectionLiveData = CheckConnectionLiveData(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set up hourly weather recycler
        val hourRecyclerViewLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val hourAdapter = HourlyRecyclerAdapter()

        // set up daily weather recycler
        val dailyLayoutManager =
            LinearLayoutManager(this)
        val dailyAdapter = DailyRecyclerAdapter(this)

     //check connection to the net
        connectionLiveData.observe(this){
            if(it){
                viewModel.getLatLang(viewModel.currentCity.value + ",IR")
                makeAppComponentVisible()
            }else{
                //no connection id = 1  loading id = 2
                showNoConnectionOrLoadingAnimVisible(1)
            }
        }


        //viewModel.getLatLang(viewModel.currentCity.value + ",IR")

        viewModel.LoadStateLiveDate.observe(this){
            when(it){
                LoadState.LOADING->showNoConnectionOrLoadingAnimVisible(2)
                LoadState.LOADED->makeAppComponentVisible()
                LoadState.ERROR->showNoConnectionOrLoadingAnimVisible(1)
            }
        }

        viewModel.LatLngLiveData.observe(this) {
            viewModel.getWholeWeatherForecast(it.coord.lat, it.coord.lon)
        }

        viewModel.WholeWeatherForcastLiveData.observe(this) {
            binding.apply {
                currentTemp.text =
                    it.current.temp.toString() + "°"

                minAndMaxTemp.text =
                    it.daily[0].temp.max.toString() + "°/" + it.daily[0].temp.min.toString() + "°"
            }
            hourAdapter.submitList(it.hourly)
            dailyAdapter.submitList(it.daily)


            val currntTimeScope =
                DateAndTimeUtils.checkTimeScope(DateAndTimeUtils.getTimeFromTimeStamp(it.current.dt)[0])
            Log.i(APP_TAG, "onCreate: $currntTimeScope ")
            changeBackground(currntTimeScope)

            dailyAdapter.currentState=currntTimeScope

            val  selectedImage = SelectImage(it.current.weather[0].icon,currntTimeScope)

            setCurrentImage(selectedImage)
        }


        binding.apply {
            viewModel.currentCity.observe(this@MainActivity) {
                selectedCityName.text = it
            }
            hourlyWeatherRecyclerView.adapter = hourAdapter
            hourlyWeatherRecyclerView.layoutManager = hourRecyclerViewLayoutManager

            dailyWeatherRecycler.adapter = dailyAdapter
            dailyWeatherRecycler.layoutManager = dailyLayoutManager

            selectedCity.setOnClickListener {
                showCityListDialog()

            }
        }

    }

    private fun showNoConnectionOrLoadingAnimVisible(eventId:Int) {
        binding.apply {
            currentWeatherImg.visibility = View.INVISIBLE
            hourlyWeatherRecyclerView.visibility = View.INVISIBLE
            dailyWeatherRecycler.visibility = View.INVISIBLE
            currentTemp.visibility = View.INVISIBLE
            minAndMaxTemp.visibility = View.INVISIBLE
            selectedCity.visibility = View.INVISIBLE
        }
        when(eventId){
            1->{
                binding.apply {
                    mainPage.background =
                        ContextCompat.getDrawable(this@MainActivity, R.drawable.no_connection_loading_bg)
                    Loading.visibility = View.INVISIBLE
                    noConnection.visibility = View.VISIBLE

                }
            }
            2->{
                binding.apply {
                    mainPage.background =
                        ContextCompat.getDrawable(this@MainActivity, R.drawable.no_connection_loading_bg)

                    noConnection.visibility = View.INVISIBLE
                    Loading.visibility = View.VISIBLE
                }
                }
        }
    }

    private fun makeAppComponentVisible() {
        binding.apply {
            currentWeatherImg.visibility = View.VISIBLE
            hourlyWeatherRecyclerView.visibility = View.VISIBLE
            dailyWeatherRecycler.visibility = View.VISIBLE
            currentTemp.visibility = View.VISIBLE
            minAndMaxTemp.visibility = View.VISIBLE
            selectedCity.visibility = View.VISIBLE
            //
            Loading.visibility = View.INVISIBLE
            noConnection.visibility = View.INVISIBLE

        }

    }


    private fun showCityListDialog() {
        CustomDialog().show(supportFragmentManager, "MyCustomFragment")

    }

    override fun onItemClicked(item: WholeWeatherForecastResponse.dailyTempModel) {

    }

    override fun onCitySelected(cityName: String) {

    }

    fun changeBackground(timeScope: String) {
        when (timeScope) {
            "dawn" -> binding.mainPage.background =
                ContextCompat.getDrawable(this, R.drawable.dawn_bg)
            "morning" -> binding.mainPage.background =
                ContextCompat.getDrawable(this, R.drawable.morning_bg)
            "noon" -> binding.mainPage.background =
                ContextCompat.getDrawable(this, R.drawable.noon_bg)
            "evening" -> binding.mainPage.background =
                ContextCompat.getDrawable(this, R.drawable.evening_bg)
            "night" -> binding.mainPage.background =
                ContextCompat.getDrawable(this, R.drawable.night_bg)


        }
    }

    private fun setCurrentImage(selectedImage: Int) {

        Glide.with(this)
            .load(selectedImage)
            .into(binding.currentWeatherImg)

    }

}