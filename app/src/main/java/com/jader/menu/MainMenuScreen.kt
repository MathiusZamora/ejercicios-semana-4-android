package com.jader.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainMenuScreen(
    onNavigateToCalculator: () -> Unit,
    onNavigateToText: () -> Unit,
    onNavigateToGuess: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Text(
                text = "Ejercicios Semana 4",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onNavigateToCalculator,
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Go to Calculator")
            }
            Button(
                onClick = onNavigateToText,
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Go to TextConverter")
            }
            Button(
                onClick = onNavigateToGuess,
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Go to GuessGame")
            }
            Text(
                text = "By: Jader Mendoza y Mathius Zamora",
                //style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.BottomEnd)
                    .padding(6.dp)
            )
        }

    }
}