package com.example.coroutine_contexts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.coroutine_contexts.ui.theme.CoroutinecontextsTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoroutinecontextsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) { Main() }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CoroutinecontextsTheme {
        Greeting("Android")
    }
}


suspend fun birdSounds(name: String, sound: String) {
    println("$name says $sound")
}

fun main() = runBlocking {
    val tweety = launch { birdSounds("Tweety", "Chirp") }
    val zazu = launch { birdSounds("Zazu", "Squawk") }
    val woodstock = launch { birdSounds("Woodstock", "Tweet tweet") }

    tweety.join()
    zazu.join()
    woodstock.join()
}