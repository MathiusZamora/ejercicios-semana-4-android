import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
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
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Calculator App",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        OutlinedTextField(
            value = multiplicationInput,
            onValueChange = {
                multiplicationInput = it.filter { char -> char.isDigit() }
                if (it.isNotEmpty()) factorialInput = ""
            },
            label = { Text("Enter number for multiplication table") },
            modifier = Modifier.fillMaxWidth(),
            enabled = factorialInput.isEmpty(),
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary,
                disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            singleLine = true
        )

        if (factorialInput.isEmpty()) {
            MultiplicationTableComposable(multiplicationInput, calculationLogic)
        }

        OutlinedTextField(
            value = factorialInput,
            onValueChange = {
                factorialInput = it.filter { char -> char.isDigit() }
                if (it.isNotEmpty()) multiplicationInput = ""
            },
            label = { Text("Enter number for factorial") },
            modifier = Modifier.fillMaxWidth(),
            enabled = multiplicationInput.isEmpty(),
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary,
                disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            singleLine = true
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
            .padding(vertical = 12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Multiplication Table",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (results.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.heightIn(max = 280.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(results) { result ->
                        Text(
                            text = "${result.input} x ${result.multiplier} = ${result.result}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.small)
                                .clickable { /* Optional: add click action */ }
                                .padding(12.dp)
                        )
                    }
                }
            } else {
                Text(
                    text = "Enter a valid number to see the table",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
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
            .padding(vertical = 12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Factorial Calculation",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = resultText,
                style = MaterialTheme.typography.bodyLarge,
                color = if (resultText.startsWith("Enter") || resultText.startsWith("Invalid") ||
                    resultText.contains("not defined") || resultText.contains("too large")
                ) {
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
