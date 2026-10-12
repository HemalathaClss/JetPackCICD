package com.example.jetpacksample1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpacksample1.dependencyinjection.DependencyInjectionActivity
import com.example.jetpacksample1.flow.FlowActivity
import com.example.jetpacksample1.livedata.LiveDataActivity
import com.example.jetpacksample1.login.LoginActivity
import com.example.jetpacksample1.sharedflow.SharedFlowActivity
import com.example.jetpacksample1.stateflow.StateFlowActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }

    @Composable
    fun MyApp() {
        MaterialTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
             //   GreetingScreen()
             //   LoginScreen()
             //   PostScreen()
             //   sample2()
             //   TodoScreen()
             //   StudentFormScreen()
                DemoLauncher()
            }
        }
    }

    /**
     * Entry screen: a menu that launches each self-contained feature demo.
     * Each button starts that package's Activity via an explicit Intent.
     */
    @Composable
    fun DemoLauncher() {
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Jetpack Reactive Demos", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(24.dp))

            DemoButton("LiveData") { context.start(LiveDataActivity::class.java) }
            Spacer(modifier = Modifier.height(12.dp))
            DemoButton("Flow") { context.start(FlowActivity::class.java) }
            Spacer(modifier = Modifier.height(12.dp))
            DemoButton("StateFlow") { context.start(StateFlowActivity::class.java) }
            Spacer(modifier = Modifier.height(12.dp))
            DemoButton("SharedFlow") { context.start(SharedFlowActivity::class.java) }
            Spacer(modifier = Modifier.height(12.dp))
            DemoButton("Dependency Injection") { context.start(DependencyInjectionActivity::class.java) }
            Spacer(modifier = Modifier.height((12.dp)))
            DemoButton("Login") {context.start(LoginActivity::class.java) }
        }
    }

    @Composable
    private fun DemoButton(label: String, onClick: () -> Unit) {
        Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
            Text(label)
        }
    }

    private fun Context.start(activity: Class<*>) {
        startActivity(Intent(this, activity))
    }

    @Composable
    fun GreetingScreen() {

        var name by remember { mutableStateOf("Compose") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Hello, $name!", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { name = "Jetpack" }) {
                Text("Change Name")
            }
        }

    }



    @Composable
    fun LoginScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Welcome Back",
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Email") }
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Password") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { }) {
                Text("Login")
            }
        }
    }



    @Composable
    fun PostScreen(viewModel: PostViewModel = viewModel()) {

        val state = viewModel.uiState

        when (state) {

            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Success -> {
                LazyColumn {
                    items(state.data) { post ->
                        Text(text = post.title)
                    }
                }
            }

            is UiState.Error -> {
                Text(text = "Error: ${state.message}")
            }
        }
    }


    @Composable
    private fun sample2(){
        var count by remember { mutableStateOf(0) }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Count: $count",
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    count++
                }
            ) {
                Text("Increment")
            }
        }
    }



    @Composable
    fun TodoScreen() {

        var task by remember { mutableStateOf("") }

        val tasks = remember {
            mutableStateListOf<String>()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = "Todo List",
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = task,
                onValueChange = { task = it },
                label = { Text("Enter Task") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (task.isNotBlank()) {
                        tasks.add(task)
                        task = ""
                    }
                }
            ) {
                Text("Add Task")
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(tasks) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = item,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }


    @Composable
    fun StudentFormScreen() {

        var name by remember { mutableStateOf("") }
        var age by remember { mutableStateOf("") }
        var submitted by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Student Registration",
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { name = it },
                label = { Text("Student Name")
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = age,
                onValueChange = { age = it },
                label = { Text("Age") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    submitted = true
                }
            ) {
                Text("Submit")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (submitted) {
                Text(
                    text = "Name: $name\nAge: $age",
                    fontSize = 18.sp
                )
            }
        }
    }
    }
