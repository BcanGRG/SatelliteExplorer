package com.bcan.satelliteexplorer.presentation.util.navigation

sealed class Screen(val route: String) {

    object SplashScreen : Screen("splash_screen")
    object ListScreen : Screen("list_screen")
    object DetailScreen : Screen("detail_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}