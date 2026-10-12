package com.example.jetpacksample1.stateflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * UI layer for the StateFlow demo.
 *
 * The screen renders a single CounterUiState. The UI is a pure function of that
 * state — change the state in the ViewModel and the UI follows automatically.
 */
class StateFlowActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    StateFlowScreen()
                }
            }
        }
    }
}

@Composable
fun StateFlowScreen(viewModel: CounterViewModel = viewModel()) {

    // Observe the whole UI state. Recomposition happens on every distinct change.
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "StateFlow", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "State holder for UI state", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "${state.count}", fontSize = 64.sp)
        Text(text = if (state.isEven) "even" else "odd", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { viewModel.decrement() }) { Text("-") }
            Button(onClick = { viewModel.increment() }) { Text("+") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(onClick = { viewModel.reset() }) { Text("Reset") }
    }
}