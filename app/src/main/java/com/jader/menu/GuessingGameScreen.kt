import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GuessingGameScreen(viewModel: GameViewModel = viewModel()) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        tonalElevation = 8.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Adivina el Número",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Text(
                text = viewModel.hint,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = viewModel.guess,
                onValueChange = { viewModel.updateGuess(it.filter { char -> char.isDigit() }) },
                label = { Text("Ingresa tu número") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                enabled = !viewModel.isGameOver,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                shape = MaterialTheme.shapes.large,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )

            Button(
                onClick = { viewModel.checkGuess() },
                enabled = !viewModel.isGameOver,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Text("Adivinar")
            }

            if (viewModel.isGameOver) {
                Button(
                    onClick = { viewModel.resetGame() },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text("Jugar de nuevo")
                }
            }
        }
    }
}
