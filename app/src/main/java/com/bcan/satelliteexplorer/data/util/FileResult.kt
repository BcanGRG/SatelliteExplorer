package com.bcan.satelliteexplorer.data.util

sealed class FileResult<out T> {

    data class OnSuccess<out T>(val data: T?) : FileResult<T>()

    data class OnError(val message: String?) : FileResult<Nothing>()

    object OnLoading : FileResult<Nothing>()
}