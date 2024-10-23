package com.example.flow_fundamentals.exam

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CountView(count: String) {
    Text(
        text = count,
        style = MaterialTheme.typography.displayLarge
    )
}

@Composable
fun StartButton(viewModel: MainViewModel, inputValue: Int = 10) {
    Button(
        onClick = {
            viewModel.startCountdown(inputValue)
        }
    ) {
        Text(text = "버튼")
    }
}