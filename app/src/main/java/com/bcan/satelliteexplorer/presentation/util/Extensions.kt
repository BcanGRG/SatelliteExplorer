package com.bcan.satelliteexplorer.presentation.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun String?.formatDate(): String {
    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd")
    val date = inputDateFormat.parse(this)
    val outputDateFormat = SimpleDateFormat("dd.MM.yyyy")

    return outputDateFormat.format(date)
}

fun Int?.formatCost(): String {
    return NumberFormat.getNumberInstance(Locale("tr", "TR")).format(this)
}