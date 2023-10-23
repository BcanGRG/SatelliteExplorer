package com.bcan.satelliteexplorer.data.repository

import com.bcan.satelliteexplorer.data.model.PositionsList
import com.bcan.satelliteexplorer.data.model.SatelliteDetail
import com.bcan.satelliteexplorer.data.model.SatelliteResponseModel
import com.bcan.satelliteexplorer.data.service.SatelliteService
import com.bcan.satelliteexplorer.data.util.FileResult
import com.bcan.satelliteexplorer.data.util.readFileRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SatelliteRepositoryImpl @Inject constructor(
    private val service: SatelliteService
) : SatelliteRepository {
    override suspend fun getSatelliteList(): Flow<FileResult<List<SatelliteResponseModel>>> {
        return readFileRequest { service.getSatellitesList() }
    }

    override suspend fun getSatelliteDetail(id: Int): Flow<FileResult<SatelliteDetail>> {
        return readFileRequest { service.getSatelliteDetail(id) }
    }

    override suspend fun getSatellitePositions(id: Int): Flow<FileResult<PositionsList>> {
        return readFileRequest { service.getSatellitePosition(id) }
    }
}