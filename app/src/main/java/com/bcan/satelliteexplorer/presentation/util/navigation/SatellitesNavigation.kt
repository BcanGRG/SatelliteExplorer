package com.bcan.satelliteexplorer.presentation.util.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bcan.satelliteexplorer.presentation.SplashScreen
import com.bcan.satelliteexplorer.presentation.detail.SatelliteDetailScreen
import com.bcan.satelliteexplorer.presentation.list.SatelliteListScreen

@Composable
fun SatellitesNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {

        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController)
        }

        composable(route = Screen.ListScreen.route) {
            SatelliteListScreen(navController)
        }

        composable(
            route = Screen.DetailScreen.route + "/{id}/{name}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                },
                navArgument("name") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            SatelliteDetailScreen(
                navController = navController,
                id = entry.arguments?.getInt("id"),
                name = entry.arguments?.getString("name")
            )
        }
    }
}