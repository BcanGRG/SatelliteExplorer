package com.bcan.satelliteexplorer.data.repository

import android.content.Context
import com.bcan.satelliteexplorer.data.model.SatelliteResponseModel
import com.bcan.satelliteexplorer.data.util.FileResult
import com.bcan.satelliteexplorer.data.util.readFromFile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SatelliteRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SatelliteRepository {
    override suspend fun getSatelliteList(): Flow<FileResult<List<SatelliteResponseModel>>> {
        return readFromFile(context, "satellites.json")
    }
}