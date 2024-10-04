package com.example.coroutine_cancellation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.coroutine_cancellation.homework.assignmenttwo.MoneyTransferScreen
import com.example.coroutine_cancellation.homework.assignmenttwo.MoneyTransferViewModel
import com.example.coroutine_cancellation.ui.theme.CoroutinecancellationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoroutinecancellationTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
                val viewModel: MoneyTransferViewModel = viewModel()

                MoneyTransferScreen(
                    state = viewModel.state,
                    onAction = viewModel::onAction
                )
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
    CoroutinecancellationTheme {
        Greeting("Android")
    }
}