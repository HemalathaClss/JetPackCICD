package com.example.jetpacksample1.sharedflow

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * UI layer for the SharedFlow demo.
 *
 * The screen collects one-time events and reacts to them (shows a Toast or
 * appends a "navigation" line to a log). Because SharedFlow keeps no state, the
 * log only grows while you are on the screen — there is nothing to "re-read".
 */
class SharedFlowActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SharedFlowScreen()
                }
            }
        }
    }
}

@Composable
fun SharedFlowScreen(viewModel: EventViewModel = viewModel()) {

    val context = LocalContext.current
    val log = remember { mutableStateListOf<String>() }

    // LaunchedEffect ties the collector to the composition's lifecycle. Each
    // emitted event is handled exactly once here — there is no replay, so events
    // are not redelivered on recomposition or configuration change.
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    log.add("Toast: ${event.message}")
                }
                is UiEvent.Navigate -> {
                    log.add("Navigate -> ${event.route}")
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "SharedFlow", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "One-time events (no state retention)", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { viewModel.onShowToastClick() }
        ) { Text("Emit Toast event") }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { viewModel.onNavigateClick() }
        ) { Text("Emit Navigation event") }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Event log:", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        if (log.isEmpty()) {
            Text(text = "(press a button)", fontSize = 14.sp)
        } else {
            log.forEach { line -> Text(text = "• $line", fontSize = 14.sp) }
        }
    }
}