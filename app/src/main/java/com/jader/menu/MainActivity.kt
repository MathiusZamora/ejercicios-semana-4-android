package com.jader.menu

import CalculatorScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController
import com.jader.menu.model.CalculationModel
import com.jader.menu.ui.theme.MenuTheme
import kotlin.random.Random

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

@Composable
fun MultiplicationTableComposable(input: String, calculationLogic: CalculationLogic) {
    var results by remember { mutableStateOf<List<CalculationModel>>(emptyList()) }

    LaunchedEffect(input) {
        results = if (input.isNotEmpty() && input.toIntOrNull() != null) {
            calculationLogic.generateMultiplicationTable(input.toInt())
        } else {
            emptyList()
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Multiplication Table",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (results.isNotEmpty()) {
                LazyColumn {
                    items(results) { result ->
                        Text(
                            text = "${result.input} x ${result.multiplier} = ${result.result}",
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            } else {
                Text(
                    text = "Enter a valid number to see the table",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun FactorialComposable(input: String, calculationLogic: CalculationLogic) {
    var resultText by remember { mutableStateOf("Enter a number to calculate factorial") }

    LaunchedEffect(input) {
        resultText = when {
            input.isEmpty() -> "Enter a number to calculate factorial"
            input.toIntOrNull() == null -> "Invalid input, please enter a positive integer"
            input.toInt() < 0 -> "Factorial is not defined for negative numbers"
            input.toInt() == 0 -> "Factorial of 0 = 1"
            input.toInt() > 20 -> "Input too large, please enter a number ≤ 20"
            else -> {
                val result = calculationLogic.calculateFactorial(input.toInt())
                "Factorial of ${result.input} = ${result.result}"
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Factorial Calculation",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = resultText,
                modifier = Modifier.padding(vertical = 4.dp),
                color = if (resultText.startsWith("Enter") || resultText.startsWith("Invalid") || resultText.contains("not defined") || resultText.contains("too large")) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }
}

class CalculationLogic {
    fun generateMultiplicationTable(number: Int): List<CalculationModel> {
        return (1..12).map { multiplier ->
            CalculationModel(
                input = number,
                multiplier = multiplier,
                result = (number * multiplier).toLong()
            )
        }
    }

    fun calculateFactorial(number: Int): CalculationModel {
        if (number <= 0) {
            return CalculationModel(input = number, result = if (number == 0) 1 else 0)
        }
        var result = 1L
        for (i in 1..number) {
            result *= i
        }
        return CalculationModel(input = number, result = result)
    }
}




@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    MenuTheme {
        CalculatorScreen()
    }
}