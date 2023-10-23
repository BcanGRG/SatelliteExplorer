package com.bcan.satelliteexplorer.data.util

import android.content.Context
import com.bcan.satelliteexplorer.data.model.PositionsList
import com.bcan.satelliteexplorer.data.model.SatelliteDetail
import com.bcan.satelliteexplorer.data.model.SatellitePositions
import com.bcan.satelliteexplorer.data.model.SatelliteResponseModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.FileNotFoundException
import java.io.IOException


suspend fun readFromFile(
    context: Context,
    filePath: String
): Flow<FileResult<List<SatelliteResponseModel>>> = flow {
    emit(FileResult.OnLoading)
    delay(3000)
    try {
        val inputString = readFile(context, filePath)
        if (!inputString.isNullOrEmpty()) {
            val type = object : TypeToken<List<SatelliteResponseModel>>() {}.type
            emit(FileResult.OnSuccess(Gson().fromJson(inputString, type)))
        } else emit(FileResult.OnError("Dosya boş veya bulunamadı"))

    } catch (exception: Exception) {
        when (exception) {
            is IOException -> emit(FileResult.OnError(exception.message))
            is FileNotFoundException -> emit(FileResult.OnError(exception.message))
            else -> emit(FileResult.OnError(exception.message))
        }
    }
}.flowOn(Dispatchers.IO)


suspend fun readFromFileDetail(
    context: Context,
    filePath: String,
    id: Int,
): Flow<FileResult<SatelliteDetail>> = flow {
    emit(FileResult.OnLoading)
    delay(3000)
    try {
        val inputString = readFile(context, filePath)
        if (!inputString.isNullOrEmpty()) {
            val type = object : TypeToken<List<SatelliteDetail>>() {}.type
            val data = Gson().fromJson(inputString, type) as List<SatelliteDetail>
            emit(FileResult.OnSuccess(data.find { it.id == id }))
        } else emit(FileResult.OnError("Dosya boş veya bulunamadı"))

    } catch (exception: Exception) {
        when (exception) {
            is IOException -> emit(FileResult.OnError(exception.message))
            is FileNotFoundException -> emit(FileResult.OnError(exception.message))
            else -> emit(FileResult.OnError(exception.message))
        }
    }
}.flowOn(Dispatchers.IO)

suspend fun readFromFilePosition(
    context: Context,
    filePath: String,
    id: Int,
): Flow<FileResult<PositionsList>> = flow {
    emit(FileResult.OnLoading)
    delay(3000)
    try {
        val inputString = readFile(context, filePath)
        if (!inputString.isNullOrEmpty()) {
            val type = object : TypeToken<SatellitePositions>() {}.type
            val data = Gson().fromJson(inputString, type) as SatellitePositions
            emit(FileResult.OnSuccess(data.positionsList.find { it.id == id.toString() }))
        } else emit(FileResult.OnError("Dosya boş veya bulunamadı"))

    } catch (exception: Exception) {
        when (exception) {
            is IOException -> emit(FileResult.OnError(exception.message))
            is FileNotFoundException -> emit(FileResult.OnError(exception.message))
            else -> emit(FileResult.OnError(exception.message))
        }
    }
}.flowOn(Dispatchers.IO)

fun readFile(
    context: Context,
    filePath: String
): String? {
    try {
        val file = context.assets.open(filePath)
        return file.bufferedReader().use { it.readText() }
    } catch (e: Exception) {
        return when (e) {
            is IOException -> null
            is FileNotFoundException -> null
            else -> null
        }
    }
}

sealed class FileResult<out T> {

    data class OnSuccess<out T>(val data: T?) : FileResult<T>()

    data class OnError(val message: String?) : FileResult<Nothing>()

    object OnLoading : FileResult<Nothing>()
}