import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class GameViewModel : ViewModel() {
    private var _secretNumber by mutableStateOf(Random.nextInt(1, 11))
    private var _guess by mutableStateOf("")
    private var _hint by mutableStateOf("Ingresa un número entre 1 y 10")
    private var _isGameOver by mutableStateOf(false)

    val secretNumber: Int get() = _secretNumber
    val guess: String get() = _guess
    val hint: String get() = _hint
    val isGameOver: Boolean get() = _isGameOver

    fun updateGuess(newGuess: String) {
        _guess = newGuess
    }

    fun checkGuess() {
        val guessNumber = _guess.toIntOrNull()
        if (guessNumber != null && guessNumber in 1..10) {
            when {
                guessNumber < _secretNumber -> _hint = "El número es mayor que $guessNumber"
                guessNumber > _secretNumber -> _hint = "El número es menor que $guessNumber"
                else -> {
                    _hint = "¡Correcto! El número era $_secretNumber"
                    _isGameOver = true
                }
            }
        } else {
            _hint = "Por favor, ingresa un número válido entre 1 y 10"
        }
    }

    fun resetGame() {
        _secretNumber = Random.nextInt(1, 11)
        _guess = ""
        _hint = "Ingresa un número entre 1 y 10"
        _isGameOver = false
    }
}