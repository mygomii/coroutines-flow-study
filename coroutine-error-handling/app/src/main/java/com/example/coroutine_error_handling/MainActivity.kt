package com.example.coroutine_error_handling

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.coroutine_error_handling.ui.theme.CoroutineerrorhandlingTheme
import com.example.coroutine_error_handling.util.EmailService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            throwable.printStackTrace()
        }


        lifecycleScope.launch(handler) {
            EmailService.addToMailingList(
                listOf(
                    "dancing.dave@email.com",
                    "caffeinated.coder@email.com",
                    "bookworm.betty@email.com",
                    "gardening.guru@email.com",
                    "sleepy.slothemail.com",
                    "hungry.hippo@email.com",
                    "clueless.cathy@email.com",
                    "techy.tom@email.com",
                    "musical.maryemail.com",
                    "adventurous.alice@email.com"
                )
            )
            EmailService.sendNewsletter()
            println("Done sending emails")
        }

        setContent {
            CoroutineerrorhandlingTheme {
            }
        }
    }
}