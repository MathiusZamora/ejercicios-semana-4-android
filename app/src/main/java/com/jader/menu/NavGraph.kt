package com.jader.menu

import CalculatorScreen
import GuessingGameScreen
import TextConverterScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "main_menu") {
        composable("main_menu") {
            MainMenuScreen(
                onNavigateToCalculator = { navController.navigate("calculator") },
                onNavigateToText = { navController.navigate("text") },
                onNavigateToGuess = { navController.navigate("GuessGame") }
            )
        }
        composable("calculator") {
            CalculatorScreen()
        }
        composable("text") {
            TextConverterScreen()
        }
        composable("GuessGame") {
            GuessingGameScreen()
        }
    }
}