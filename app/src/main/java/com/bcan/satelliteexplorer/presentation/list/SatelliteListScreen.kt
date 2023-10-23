package com.bcan.satelliteexplorer.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bcan.satelliteexplorer.presentation.ui.SatelliteContent
import com.bcan.satelliteexplorer.presentation.util.navigation.Screen
import kotlin.random.Random

@Composable
fun SatelliteListScreen(
    navController: NavController,
    viewModel: SatelliteListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val satellites by viewModel.satellites.collectAsStateWithLifecycle()

    SatelliteContent {

        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = searchText,
                onValueChange = viewModel::onSearchTextChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                placeholder = { Text(text = "Search") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon"
                    )
                },
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = Color.Black,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    backgroundColor = Color.White
                )
            )

            uiState.errorMessage?.let {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = it, modifier = Modifier.align(Alignment.Center))
                }
            }

            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    satellites?.let {
                        itemsIndexed(it, key = { _, itemKey -> itemKey.id ?: Random.nextInt() }
                        ) { index, item ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate(
                                            Screen.DetailScreen.withArgs(
                                                item.id.toString(),
                                                "${item.name}"
                                            )
                                        )
                                    }
                            ) {
                                SatelliteItem(
                                    modifier = Modifier
                                        .padding(vertical = 12.dp)
                                        .width(120.dp)
                                        .align(Alignment.Center),
                                    satelliteName = item.name,
                                    isSatelliteActive = item.active,
                                )
                            }
                            if (index < it.lastIndex)
                                Divider(color = Color.Black, thickness = 1.dp)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SatelliteItem(
    modifier: Modifier = Modifier,
    satelliteName: String?,
    isSatelliteActive: Boolean?,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val isActiveText = if (isSatelliteActive == true) {
            "Active"
        } else "Passive"
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(
                    brush =
                    if (isSatelliteActive == true) SolidColor(Color.Green)
                    else SolidColor(Color.Red),
                    shape = CircleShape
                )
                .align(Alignment.CenterVertically)
        )
        Column {
            Text(
                text = "$satelliteName",
                style = if (isSatelliteActive == false) TextStyle(
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                ) else TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = isActiveText,
                style = if (isSatelliteActive == false) TextStyle(
                    color = Color.Gray,
                ) else TextStyle()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSatelliteItemPassive() {
    SatelliteItem(
        satelliteName = "Starship-1",
        isSatelliteActive = false,
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSatelliteItemActive() {
    SatelliteItem(
        satelliteName = "Dragon-1",
        isSatelliteActive = true,
    )
}