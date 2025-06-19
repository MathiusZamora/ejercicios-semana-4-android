import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jader.menu.model.CalculationModel

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    var multiplicationInput by remember { mutableStateOf("") }
    var factorialInput by remember { mutableStateOf("") }
    val calculationLogic = CalculationLogic()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Calculator App",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = multiplicationInput,
            onValueChange = {
                multiplicationInput = it.filter { char -> char.isDigit() }
                if (it.isNotEmpty()) factorialInput = "" // Clear factorial input when multiplication is edited
            },
            label = { Text("Enter number for multiplication table") },
            modifier = Modifier.fillMaxWidth(),
            enabled = factorialInput.isEmpty() // Disable when factorial input is not empty
        )

        if (factorialInput.isEmpty()) {
            MultiplicationTableComposable(multiplicationInput, calculationLogic)
        }

        OutlinedTextField(
            value = factorialInput,
            onValueChange = {
                factorialInput = it.filter { char -> char.isDigit() }
                if (it.isNotEmpty()) multiplicationInput = "" // Clear multiplication input when factorial is edited
            },
            label = { Text("Enter number for factorial") },
            modifier = Modifier.fillMaxWidth(),
            enabled = multiplicationInput.isEmpty() // Disable when multiplication input is not empty
        )

        if (multiplicationInput.isEmpty()) {
            FactorialComposable(factorialInput, calculationLogic)
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