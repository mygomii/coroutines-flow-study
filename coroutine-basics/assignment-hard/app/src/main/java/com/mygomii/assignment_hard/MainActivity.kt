package com.mygomii.assignment_hard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mygomii.assignment_hard.ui.theme.AssignmenthardTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val Coo = "coo"
private const val Caw = "Caw"
private const val Chirp = "Chirp"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AssignmenthardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Screen()
                }
            }
        }
    }
}


@Composable
fun Screen() {
    var selectedBird by remember { mutableStateOf("") }
    var job by remember { mutableStateOf<Job?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = selectedBird)

        LaunchedEffect(selectedBird) {
            job?.cancel()
            job = coroutineScope.launch {
                while (true) {
                    println(selectedBird)
                    delay(3000)
                }
            }
        }

        Button(Coo) {
            selectedBird = Coo
        }

        Button(Caw) {
            selectedBird = Caw
        }

        Button(Chirp) {
            selectedBird = Chirp
        }
    }
}

@Composable
fun Button(sound: String, onClick: (String) -> Unit) {
    Button(onClick = {
        onClick(sound)
    }) {
        Text(sound)
    }
}
