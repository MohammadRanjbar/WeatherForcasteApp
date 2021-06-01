package com.myapps.testapp.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.espinas.raman.ui.HomePage.adapters.CityRecAdapter
import com.myapps.testapp.R

class CustomDialog: DialogFragment(),CityRecAdapter.onCityNameClickListener {

    private val viewModel: ActivityViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getDialog()!!.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
         val view =inflater.inflate(R.layout.city_dialog_layout, container, false)
        // set up city recycler
        val cityRecyclerLayoutManager = LinearLayoutManager(requireContext())
        val cityAdapter = CityRecAdapter(this)

        val cityRec = view.findViewById<RecyclerView>(R.id.city_recycler_view)

        cityRec.layoutManager = cityRecyclerLayoutManager
        cityRec.adapter = cityAdapter


       return view
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

    }

    override fun onCitySelected(cityName: String) {
        viewModel.getLatLang(cityName+",IR")
        viewModel.currentCity.value = cityName
        dialog!!.dismiss()

    }
}