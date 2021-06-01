package com.espinas.raman.ui.HomePage.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myapps.testapp.databinding.CityRecyclerItemBinding

class CityRecAdapter(
    private val listener: onCityNameClickListener
) :
    RecyclerView.Adapter<CityRecAdapter.ViewHolder>() {
    val cities = listOf(
        "Tabriz","Urmia","Ardabil","Zanjan", "Tehran", "Shiraz", "Esfahan", "Mashhad","Hamedan"
    )
    var currentScope:String=""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CityRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = cities[position]
        holder.bind(currentItem)

    }

    override fun getItemCount() = cities.size

    inner class ViewHolder(private val binding: CityRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = cities[position]
                    listener.onCitySelected(item)
                }
            }
        }

        fun bind(item: String) {
            binding.apply {
              cityName.text = item
            }
        }

    }

    interface onCityNameClickListener {
        fun onCitySelected(cityName:String)
    }
}