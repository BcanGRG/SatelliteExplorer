package com.bcan.satelliteexplorer.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bcan.satelliteexplorer.R
import com.bcan.satelliteexplorer.presentation.util.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        ), label = ""
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        navController.navigate(Screen.ListScreen.route)
    }
    Splash(alpha = alphaAnim.value)

}

@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier
            .background(Color(0xFF654baa))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.satellite_logo),
            contentDescription = "splash logo",
            modifier = Modifier
                .size(120.dp)
                .alpha(alpha),
        )

    }
}

@Composable
@Preview(showBackground = true)
fun SplashScreenPreview() {
    Splash(alpha = 1f)
}