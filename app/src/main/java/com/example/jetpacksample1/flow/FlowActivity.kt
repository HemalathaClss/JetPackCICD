package com.example.jetpacksample1.flow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
 * UI layer for the Flow demo.
 *
 * Shows numbers arriving one-per-second after pressing Start. Press again and the
 * sequence restarts from 1 — the visible proof that a Flow is a cold stream.
 */
class FlowActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    FlowScreen()
                }
            }
        }
    }
}

@Composable
fun FlowScreen(viewModel: FlowViewModel = viewModel()) {

    // collectAsStateWithLifecycle() collects the flow only while the UI is at
    // least STARTED, pausing in the background — the recommended collector in UI.
    val numbers by viewModel.numbers.collectAsStateWithLifecycle()
    val isRunning by viewModel.isRunning.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Flow", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Cold asynchronous stream", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = if (numbers.isEmpty()) "—" else numbers.joinToString("  "),
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { viewModel.startCollecting() },
            enabled = !isRunning
        ) {
            Text(if (isRunning) "Collecting..." else "Start (collect cold flow)")
        }
    }
}