package com.bcan.satelliteexplorer.data.service

import android.content.Context
import com.bcan.satelliteexplorer.data.model.PositionsList
import com.bcan.satelliteexplorer.data.model.SatelliteDetail
import com.bcan.satelliteexplorer.data.model.SatellitePositions
import com.bcan.satelliteexplorer.data.model.SatelliteResponseModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class SatelliteService @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun getSatellitesList(): List<SatelliteResponseModel>? {
        return try {
            val file = context.assets.open("satellites.json")
            val inputString = file.bufferedReader().use { it.readText() }
            val type = object : TypeToken<List<SatelliteResponseModel>>() {}.type
            Gson().fromJson(inputString, type) as List<SatelliteResponseModel>
        } catch (e: Exception) {
            when (e) {
                is IOException -> null
                else -> null
            }
        }
    }

    fun getSatelliteDetail(id: Int): SatelliteDetail? {
        return try {
            val file = context.assets.open("satellitedetail.json")
            val inputString = file.bufferedReader().use { it.readText() }
            val type = object : TypeToken<List<SatelliteDetail>>() {}.type
            val data = Gson().fromJson(inputString, type) as List<SatelliteDetail>
            data.find { it.id == id }
        } catch (e: Exception) {
            when (e) {
                is IOException -> null
                else -> null
            }
        }
    }

    fun getSatellitePosition(id: Int): PositionsList? {
        return try {
            val file = context.assets.open("positions.json")
            val inputString = file.bufferedReader().use { it.readText() }
            val type = object : TypeToken<SatellitePositions>() {}.type
            val data = Gson().fromJson(inputString, type) as SatellitePositions
            data.positionsList.find { it.id == id.toString() }
        } catch (e: Exception) {
            when (e) {
                is IOException -> null
                else -> null
            }
        }
    }
}