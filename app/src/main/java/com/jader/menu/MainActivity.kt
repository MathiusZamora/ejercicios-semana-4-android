package com.jader.menu

import CalculatorScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.jader.menu.ui.theme.MenuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MenuTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    MenuTheme {
        CalculatorScreen()
    }
}