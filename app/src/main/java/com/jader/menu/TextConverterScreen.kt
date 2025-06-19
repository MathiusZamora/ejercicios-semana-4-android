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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun TextConverterScreen() {
    var textInput by remember { mutableStateOf(TextFieldValue("")) }
    val uppercaseText = textInput.text.uppercase()
    val lowercaseText = textInput.text.lowercase()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Text Converter",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = textInput,
            onValueChange = { textInput = it },
            label = { Text("Enter your phrase") },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Uppercase: $uppercaseText",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Lowercase: $lowercaseText",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}