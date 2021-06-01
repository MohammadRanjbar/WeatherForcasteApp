package com.espinas.raman.ui.CustomerInsurancePage

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myapps.testapp.databinding.DailyWeatherForcastItemBinding
import com.myapps.testapp.models.WholeWeatherForecastResponse.*
import com.myapps.testapp.utils.APP_TAG
import com.myapps.testapp.utils.DateAndTimeUtils
import com.myapps.testapp.utils.SelectImage
import java.text.NumberFormat
import java.util.*


class DailyRecyclerAdapter(private val listener: onUserInsuranceItemClicked) :
    ListAdapter<dailyTempModel, DailyRecyclerAdapter.ViewHolder>(DiffCallBack()) {


         var currentState :String =""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DailyWeatherForcastItemBinding.inflate(
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

    inner class ViewHolder(private val binding: DailyWeatherForcastItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClicked(item)
                    }
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: dailyTempModel) {
            binding.apply {
                if (adapterPosition == 0) {
                    dayName.visibility = View.GONE
                    dayMaxTemp.visibility = View.GONE
                    dayMinTemp.visibility = View.GONE
                    imageView.visibility = View.GONE
                } else {
                    if (adapterPosition == 1) {
                        dayName.text = "Tomorrow"
                        dayMaxTemp.text = item.temp.max.toString() + "°"
                        dayMinTemp.text = "/" + item.temp.min.toString() + "°"
                        Log.i(APP_TAG, "bind: $currentState ${item.weather[0].icon}")
                        Glide.with(itemView)
                            .load(SelectImage(item.weather[0].icon))
                            .into(imageView)

                    } else {
                        dayName.text = DateAndTimeUtils.getTimeFromTimeStamp(item.dt)[2]
                        dayMaxTemp.text = item.temp.max.toString() + "°"
                        dayMinTemp.text = "/" + item.temp.min.toString() + "°"
                        Log.i(APP_TAG, "bind: $currentState ${item.weather[0].icon}")
                        Glide.with(itemView)
                            .load(SelectImage(item.weather[0].icon))
                            .into(imageView)
                    }
                }
            }

        }

    }


    interface onUserInsuranceItemClicked {
        fun onItemClicked(item: dailyTempModel)

    }

    private class DiffCallBack : DiffUtil.ItemCallback<dailyTempModel>() {
        override fun areItemsTheSame(
            oldItem: dailyTempModel,
            newItem: dailyTempModel
        ) = oldItem.dt == newItem.dt

        override fun areContentsTheSame(
            oldItem: dailyTempModel,
            newItem: dailyTempModel
        ) = oldItem == newItem
    }
}


