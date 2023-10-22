package com.bcan.satelliteexplorer.data.model

import com.google.gson.annotations.SerializedName

data class SatelliteResponseModel(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("active") var active: Boolean? = null,
    @SerializedName("name") var name: String? = null,
)
