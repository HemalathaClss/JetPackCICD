package com.example.jetpacksample1.livedata

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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * UI layer for the LiveData demo.
 *
 * A counter screen: the value lives in the ViewModel as LiveData and the UI just
 * observes it. Rotate the device and the value survives (ViewModel) and the UI
 * re-reads the latest value (LiveData) — lifecycle-aware updates in action.
 */
class LiveDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LiveDataScreen()
                }
            }
        }
    }
}

@Composable
fun LiveDataScreen(viewModel: CounterViewModel = viewModel()) {

    // observeAsState() bridges LiveData -> Compose State. It subscribes when the
    // composition enters the screen and automatically unsubscribes when it leaves
    // — that is the "lifecycle-aware" behaviour. Every emitted value triggers a
    // recomposition with the latest count.
    val count by viewModel.count.observeAsState(0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "LiveData", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Lifecycle-aware observable data", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "$count", fontSize = 64.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { viewModel.decrement() }) { Text("-") }
            Button(onClick = { viewModel.increment() }) { Text("+") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(onClick = { viewModel.reset() }) { Text("Reset") }
    }
}