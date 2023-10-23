package com.bcan.satelliteexplorer.data.model

import com.google.gson.annotations.SerializedName

data class SatelliteDetail(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("cost_per_launch") var costPerLaunch: Int? = null,
    @SerializedName("first_flight") var firstFlight: String? = null,
    @SerializedName("height") var height: Int? = null,
    @SerializedName("mass") var mass: Int? = null,
)
