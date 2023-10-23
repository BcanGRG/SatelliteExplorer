package com.bcan.satelliteexplorer.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bcan.satelliteexplorer.presentation.ui.SatelliteContent

@Composable
fun SatelliteDetailScreen(
    navController: NavController,
    viewModel: SatelliteDetailViewModel = hiltViewModel(),
    id: Int?,
    name: String?,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val satellite = uiState.item
    val positions by viewModel.positions.collectAsStateWithLifecycle()
    val positionsList = positions.positionsList?.positions
    val currentPositionIndex = positions.currentPositionIndex
    val currentPosition = positionsList?.get(currentPositionIndex)

    SatelliteContent {
        id?.let {
            LaunchedEffect(key1 = it) {
                viewModel.getSatelliteDetail(it)
                viewModel.getSatellitePositions(it)
            }
        }


        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            uiState.errorMessage?.let {
                Box(modifier = Modifier.fillMaxSize()) {
                    androidx.compose.material.Text(
                        text = it,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                name?.let {
                    Text(
                        text = it, style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                satellite?.let { sat ->
                    Text(
                        text = "${sat.firstFlight}", style = TextStyle(
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    AnnotatedTitleValueText(
                        title = "Height/Mass",
                        value = "${sat.height}/${sat.mass}"
                    )
                    AnnotatedTitleValueText(
                        title = "Cost",
                        value = "${sat.costPerLaunch}"
                    )
                }

                positionsList?.let { pos ->
                    AnnotatedTitleValueText(
                        title = "Last Position",
                        value = "(${currentPosition?.posX},${currentPosition?.posY})"
                    )
                }

            }
        }
    }
}

@Composable
fun AnnotatedTitleValueText(
    title: String,
    value: String
) {
    val text = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        ) {
            append("$title:")
        }
        withStyle(style = SpanStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal)) {
            append(value)
        }
    }

    Text(text = text)
}