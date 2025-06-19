package com.jader.menu

import CalculatorScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "main_menu") {
        composable("main_menu") {
            MainMenuScreen(
                onNavigateToCalculator = { navController.navigate("calculator") }
            )
        }
        composable("calculator") {
            CalculatorScreen()
        }
    }
}