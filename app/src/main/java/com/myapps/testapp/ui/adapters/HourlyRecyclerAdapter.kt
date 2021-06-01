package com.espinas.raman.ui.CustomerInsurancePage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myapps.testapp.databinding.HourlyWeatherForcastItemBinding
import com.myapps.testapp.models.WholeWeatherForecastResponse
import com.myapps.testapp.utils.DateAndTimeUtils
import com.myapps.testapp.utils.SelectImage

class HourlyRecyclerAdapter :
    ListAdapter<WholeWeatherForecastResponse.hourlyTempModel, HourlyRecyclerAdapter.ViewHolder>(DiffCallBack()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HourlyWeatherForcastItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ViewHolder(private val binding: HourlyWeatherForcastItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: WholeWeatherForecastResponse.hourlyTempModel) {
            binding.apply {
                Glide.with(itemView).load(SelectImage(item.weather[0].icon,DateAndTimeUtils.checkTimeScope(DateAndTimeUtils.getTimeFromTimeStamp(item.dt)[0]))).into(hourlyWeatherImg)
                hourlyWeatherTemp.text = item.temp.toString()+"°"
                hourWeaterHourTxt.text = DateAndTimeUtils.getTimeFromTimeStamp(item.dt)[1]
                }

            }

        }



   private class DiffCallBack : DiffUtil.ItemCallback<WholeWeatherForecastResponse.hourlyTempModel>() {
        override fun areItemsTheSame(
            oldItem: WholeWeatherForecastResponse.hourlyTempModel,
            newItem: WholeWeatherForecastResponse.hourlyTempModel
        ) = oldItem.dt == newItem.dt

        override fun areContentsTheSame(
            oldItem: WholeWeatherForecastResponse.hourlyTempModel,
            newItem: WholeWeatherForecastResponse.hourlyTempModel
        ) = oldItem == newItem
    }



    }


