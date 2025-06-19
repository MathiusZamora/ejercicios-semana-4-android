import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jader.menu.CalculationLogic
import com.jader.menu.FactorialComposable
import com.jader.menu.MultiplicationTableComposable

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
            onValueChange = { multiplicationInput = it.filter { char -> char.isDigit() } },
            label = { Text("Enter number for multiplication table") },
            modifier = Modifier.fillMaxWidth()
        )

        MultiplicationTableComposable(multiplicationInput, calculationLogic)

        OutlinedTextField(
            value = factorialInput,
            onValueChange = { factorialInput = it.filter { char -> char.isDigit() } },
            label = { Text("Enter number for factorial") },
            modifier = Modifier.fillMaxWidth()
        )

        FactorialComposable(factorialInput, calculationLogic)
    }
}