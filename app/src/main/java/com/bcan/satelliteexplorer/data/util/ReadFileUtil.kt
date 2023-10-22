package com.bcan.satelliteexplorer.data.util

import android.content.Context
import com.bcan.satelliteexplorer.data.model.SatelliteResponseModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.io.IOException


suspend fun readFromFile(
    context: Context,
    filePath: String
): Flow<FileResult<List<SatelliteResponseModel>>> = flow {
    emit(FileResult.OnLoading)
    try {
        val file = context.assets.open(filePath)
        val inputString = file.bufferedReader().use { it.readText() }
        if (inputString.isNotEmpty()) {
            val type = object : TypeToken<List<SatelliteResponseModel>>() {}.type
            emit(FileResult.OnSuccess(Gson().fromJson(inputString, type)))
        } else {
            emit(FileResult.OnError("Dosya boş veya okuma hatası"))
        }
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> emit(FileResult.OnError(throwable.message))
            else -> emit(FileResult.OnError(throwable.message))
        }
    }
}.flowOn(Dispatchers.IO)

fun readFile(filePath: String): String {
    val file = File(filePath)
    val inputStream = file.inputStream()
    return inputStream.bufferedReader().use { it.readText() }
}

sealed class FileResult<out T> {

    data class OnSuccess<out T>(val data: T?) : FileResult<T>()

    data class OnError(val message: String?) : FileResult<Nothing>()

    object OnLoading : FileResult<Nothing>()
}