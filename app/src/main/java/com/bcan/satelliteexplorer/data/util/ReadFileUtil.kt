package com.bcan.satelliteexplorer.data.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException


fun <T> readFileRequest(
    serviceCall: suspend () -> T?
) = flow {
    emit(FileResult.OnLoading)
    emit(safeReadFile() { serviceCall() })
}.flowOn(Dispatchers.IO)

suspend fun <T> safeReadFile(
    readFile: suspend () -> T?
): FileResult<T> {
    return try {
        val result = readFile()
        if (result != null) {
            FileResult.OnSuccess(result)
        } else FileResult.OnError("Dosya boş veya bulunamadı.")
    } catch (exception: Exception) {
        when (exception) {
            is IOException -> FileResult.OnError(exception.message)
            else -> FileResult.OnError(exception.message)
        }
    }
}
