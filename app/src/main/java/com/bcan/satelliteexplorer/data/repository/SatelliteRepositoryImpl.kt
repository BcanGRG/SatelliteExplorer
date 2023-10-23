package com.bcan.satelliteexplorer.data.repository

import android.content.Context
import com.bcan.satelliteexplorer.data.model.PositionsList
import com.bcan.satelliteexplorer.data.model.SatelliteDetail
import com.bcan.satelliteexplorer.data.model.SatelliteResponseModel
import com.bcan.satelliteexplorer.data.util.FileResult
import com.bcan.satelliteexplorer.data.util.readFromFile
import com.bcan.satelliteexplorer.data.util.readFromFileDetail
import com.bcan.satelliteexplorer.data.util.readFromFilePosition
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SatelliteRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SatelliteRepository {
    override suspend fun getSatelliteList(): Flow<FileResult<List<SatelliteResponseModel>>> {
        return readFromFile(context, "satellites.json")
    }

    override suspend fun getSatelliteDetail(id: Int): Flow<FileResult<SatelliteDetail>> {
        return readFromFileDetail(context, "satellitedetail.json", id)
    }

    override suspend fun getSatellitePositions(id: Int): Flow<FileResult<PositionsList>> {
        return readFromFilePosition(context, "positions.json", id)
    }
}