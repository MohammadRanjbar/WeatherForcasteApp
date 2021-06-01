package com.myapps.testapp.utils

import com.myapps.testapp.R

fun SelectImage(iconId: String, timeScope: String): Int {


    if (iconId in setOf(
            "03d", "03n",
            "04d", "04n",
            "09d", "09n",
            "10d", "10n",
            "11d", "11n",
            "13d", "13n",
            "50d", "50n"
        )
    ) {
        if (iconId.contains("03")) {
            return R.drawable.ic_scattered_clouds

        } else if (iconId.contains("04")) {
            return R.drawable.ic_broken_cloud

        } else if (iconId.contains("09")) {
            return R.drawable.ic_shower_raint

        } else if (iconId.contains("10")) {
            return R.drawable.ic_rain

        } else if (iconId.contains("11")) {
            return R.drawable.ic_thunderstorm

        } else if (iconId.contains("13")) {
            return R.drawable.ic_snow

        } else if (iconId.contains("50")) {
            return R.drawable.ic_mist

        }

    } else {

        if (iconId.contains("01")) {
            when (timeScope) {
                "dawn" -> {
                    return R.drawable.ic_clear_sky_dawn
                }
                "morning" -> {
                    return R.drawable.ic_clear_sky_morning
                }

                "evening" -> {
                    return R.drawable.ic_clear_sky_evening
                }
                "night" -> {
                    return R.drawable.ic_clear_sky_night
                }
            }
        } else if (iconId.contains("02")) {

            when (timeScope) {
                "dawn" -> {
                    return R.drawable.ic_few_cloud_dawn
                }
                "morning" -> {
                    return R.drawable.ic_few_cloud_morning
                }

                "evening" -> {
                    return R.drawable.ic_few_cloud_evening
                }
                "night" -> {
                    return R.drawable.ic_clear_sky_night
                }
            }
        }

    }

    return 0
}

fun SelectImage(iconId: String): Int {

    if (iconId.contains("01")) {
        return R.drawable.ic_clear_sky_morning


    } else if (iconId.contains("02")) {
        return R.drawable.ic_few_cloud_morning

    } else if (iconId.contains("03")) {
        return R.drawable.ic_scattered_clouds

    } else if (iconId.contains("04")) {
        return R.drawable.ic_broken_cloud

    } else if (iconId.contains("09")) {
        return R.drawable.ic_shower_raint

    } else if (iconId.contains("10")) {
        return R.drawable.ic_rain

    } else if (iconId.contains("11")) {
        return R.drawable.ic_thunderstorm

    } else if (iconId.contains("13")) {
        return R.drawable.ic_snow

    } else if (iconId.contains("50")) {
        return R.drawable.ic_mist

    }

return 0
}
