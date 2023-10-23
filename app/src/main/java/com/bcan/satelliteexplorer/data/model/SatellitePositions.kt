package com.bcan.satelliteexplorer.data.model

import com.google.gson.annotations.SerializedName


data class SatellitePositions(

    @SerializedName("list") var positionsList: ArrayList<PositionsList> = arrayListOf()

)

data class PositionsList(

    @SerializedName("id") var id: String? = null,
    @SerializedName("positions") var positions: ArrayList<Positions> = arrayListOf()

)

data class Positions(

    @SerializedName("posX") var posX: Double? = null,
    @SerializedName("posY") var posY: Double? = null

)

