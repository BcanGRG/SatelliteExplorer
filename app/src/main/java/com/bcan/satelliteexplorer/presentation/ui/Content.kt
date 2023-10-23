package com.bcan.satelliteexplorer.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SatelliteContent(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.LightGray,
    composable: @Composable () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = backgroundColor
        ) {
            composable()
        }
    }
}