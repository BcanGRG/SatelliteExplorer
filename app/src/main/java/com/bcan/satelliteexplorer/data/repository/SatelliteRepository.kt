package com.bcan.satelliteexplorer.data.repository

import com.bcan.satelliteexplorer.data.model.PositionsList
import com.bcan.satelliteexplorer.data.model.SatelliteDetail
import com.bcan.satelliteexplorer.data.model.SatelliteResponseModel
import com.bcan.satelliteexplorer.data.util.FileResult
import kotlinx.coroutines.flow.Flow

interface SatelliteRepository {

    suspend fun getSatelliteList(): Flow<FileResult<List<SatelliteResponseModel>>>
    suspend fun getSatelliteDetail(id: Int): Flow<FileResult<SatelliteDetail>>
    suspend fun getSatellitePositions(id: Int): Flow<FileResult<PositionsList>>
}