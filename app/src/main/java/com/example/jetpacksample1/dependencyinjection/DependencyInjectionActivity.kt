package com.example.jetpacksample1.dependencyinjection

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Dependency Injection demo — the UI layer.
 *
 * Sample task: type a name, press the button, and an injected repository
 * produces the greeting. The UI never touches `RealGreetingRepository` — it
 * only talks to the ViewModel, which in turn only talks to the injected
 * interface. Each layer depends on an abstraction, not a concrete class.
 */
class DependencyInjectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DependencyInjectionScreen()
                }
            }
        }
    }
}

@Composable
fun DependencyInjectionScreen(
    // Here is where injection is triggered for the UI: we pass our custom
    // factory to viewModel(). The factory decides which repository the
    // ViewModel gets — the ViewModel does not build it itself.
    viewModel: GreetingViewModel = viewModel(factory = GreetingViewModelFactory())
) {
    // Local UI-only state for the text field (not part of the DI lesson).
    var name by remember { mutableStateOf("") }

    // State exposed by the ViewModel, collected safely with the lifecycle.
    val greeting by viewModel.greeting.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Dependency Injection", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "ViewModel receives its repository via constructor injection", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Your name") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { viewModel.greet(name) }) {
            Text("Get greeting")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = greeting, fontSize = 18.sp)
    }
}