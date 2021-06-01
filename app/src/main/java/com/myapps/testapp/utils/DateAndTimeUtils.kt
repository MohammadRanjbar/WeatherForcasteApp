package com.myapps.testapp.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


object DateAndTimeUtils {


    fun getTimeFromTimeStamp(timeStamp: Long): ArrayList<String> {
        val array = ArrayList<String>()
        val calendar = Calendar.getInstance(Locale.US)
        calendar.time = Date(timeStamp * 1000)
//        Log.i(APP_TAG, "getTimeFromTimeStamp:${calendar.time} ")
        val sdf = SimpleDateFormat("kk:mm:ss", Locale.US)
        val time = sdf.format(calendar.time)
        val sdfInPM = SimpleDateFormat("h a", Locale.US)
        val timeInPm = sdfInPM.format(calendar.time)
        val sdfDayName = SimpleDateFormat("EEEE", Locale.US)
        val dayName = sdfDayName.format(calendar.time)


        array.add(time)
        array.add(timeInPm)
        array.add(dayName)


       // checkTimeScope(time)

        return array
    }


    fun checkTimeScope(time:String):String {

        val listOfTimes = listOf(
            "00:00:00" to "06:59:00",
            "07:00:00" to "11:59:00",
            "12:00:00" to "16:59:00",
            "17:00:00" to "19:59:00",
            "20:00:00" to "23:59:00",
        )



        val currentTime = Calendar.getInstance(Locale.US)
        val parts: List<String> = time.split(":")

        currentTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts[0]));
        currentTime.set(Calendar.MINUTE, Integer.parseInt(parts[1]));
        currentTime.set(Calendar.SECOND,Integer.parseInt(parts[2]) );


        var j = 0

        for (i in listOfTimes){
            j++

            val firstTime =Calendar.getInstance(Locale.US)

            val partFirst: List<String> = i.first.split(":")

            firstTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(partFirst[0]))
            firstTime.set(Calendar.MINUTE, Integer.parseInt(partFirst[1]))
            firstTime.set(Calendar.SECOND,Integer.parseInt(partFirst[2]) )

            val SecondTime =Calendar.getInstance(Locale.US)

            val partSecond: List<String> = i.second.split(":")

            SecondTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(partSecond[0]))
            SecondTime.set(Calendar.MINUTE, Integer.parseInt(partSecond[1]))
            SecondTime.set(Calendar.SECOND,Integer.parseInt(partSecond[2]) )

            Log.i(APP_TAG, "checkTimeScope: ${firstTime.time}   ${SecondTime.time} ")

            if (currentTime.after(firstTime) && currentTime.before(SecondTime)){
                when(j){
                    1-> return "dawn"
                    2-> return "morning"
                    3-> return "noon"
                    4-> return "evening"
                    5-> return "night"

                }


            }
        }

      return ""
    }
}