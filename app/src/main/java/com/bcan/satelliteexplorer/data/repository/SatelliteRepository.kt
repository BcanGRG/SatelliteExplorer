package com.bcan.satelliteexplorer.data.repository

import com.bcan.satelliteexplorer.data.model.SatelliteResponseModel
import com.bcan.satelliteexplorer.data.util.FileResult
import kotlinx.coroutines.flow.Flow

interface SatelliteRepository {

    suspend fun getSatelliteList(): Flow<FileResult<List<SatelliteResponseModel>>>
}