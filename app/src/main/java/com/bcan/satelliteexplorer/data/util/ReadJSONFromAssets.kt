package com.bcan.satelliteexplorer.data.util

import android.content.Context
import com.bcan.satelliteexplorer.data.model.SatelliteResponseModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


fun readJSONFromAssets(context: Context, path: String): List<SatelliteResponseModel>? {
    return try {
        val file = context.assets.open(path)

        val inputString = file.bufferedReader().use { it.readText() }

        val type = object : TypeToken<List<SatelliteResponseModel>>() {}.type
        Gson().fromJson(inputString, type)

    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}