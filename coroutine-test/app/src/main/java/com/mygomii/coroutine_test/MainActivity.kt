package com.mygomii.coroutine_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.mygomii.coroutine_test.ui.theme.CoroutinetestTheme
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


//        val job = lifecycleScope.launch {
//            var i = 0
//            while (i < 10) {
//                delay(1000)
//                i++
//            }
//        }
//
//        job.join() // 완료 대기
//        job.cancel()

        val job = lifecycleScope.launch {
            val innerJob1 = launch {
                delay(2000L)
                println("Inner job 1 finished")
            }
            val innerJob2 = launch {
                delay(1000L)
                println("Inner job 2 finished")
            }

//            val profileDeferred = async {
//                println("Fetching profile data...")
//                delay(2000L)
//                "profile"
//            }
//            val postsDeferred = async {
//                println("Fetching profile posts...")
//                delay(3000L)
//                "posts"
//            }

//            val timeMillis = measureTimeMillis {
//                val posts = postsDeferred.await()
//                val profile = profileDeferred.await()
//
//                println("Profile loaded: $profile, $posts")
//            }
//            println("Jobs took $timeMillis milliseconds.")
        }


        setContent {
            CoroutinetestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
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
    CoroutinetestTheme {
        Greeting("Android")
    }
}