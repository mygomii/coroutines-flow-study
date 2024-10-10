package com.example.coroutine_learned_so_far

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import com.example.coroutine_learned_so_far.ui.theme.CoroutinelearnedsofarTheme
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.cancellation.CancellationException


private const val AUTHENTICATION_TIMEOUT_MILLIS = 3000L

class MainActivity : AppCompatActivity() {
    private val promptManager by lazy {
        BiometricPromptManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BiometricPromptManager(this)
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ),
//            0
//        )
//
//        lifecycleScope.launch {
//            val job = launch {
//                val location = getLocation()
//                println("Location: (${location.latitude}, ${location.longitude})")
//            }
//            delay(5L)
//            job.cancel()
//        }
//
//        setContent {
//            CoroutinelearnedsofarTheme {}
//        }


        setContent {
            CoroutinelearnedsofarTheme {
                val context = LocalContext.current
                var biometricsResult by remember {
                    mutableStateOf<BiometricResult?>(null)
                }

                LaunchedEffect(biometricsResult) {
                    if (biometricsResult != null) {
                        Toast.makeText(context, biometricsResult.toString(), Toast.LENGTH_LONG)
                            .show()
                        biometricsResult = null
                    }
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Button(onClick = {
                        lifecycleScope.launch {
                            launch {
                                val result = withTimeoutOrNull(AUTHENTICATION_TIMEOUT_MILLIS) {
                                    try {
                                        biometricsResult = promptManager.showBiometricPrompt(
                                            title = "Authentication",
                                            description = "Please authenticate to proceed"
                                        )

                                    } catch (e: Exception) {
                                        if (e is CancellationException) {
                                            Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG)
                                                .show()
                                            throw e
                                        }
                                        e.printStackTrace()
                                    }
                                }
                                if (result == null) {
                                    println("Timeout reached, cancelling...")
                                    lifecycleScope.cancel()
                                }
                            }
                        }
                    }) {
                        Text(text = "Authenticate")
                    }
                }
            }
        }
    }
}
//
//
//@RequiresApi(Build.VERSION_CODES.R)
//suspend fun Context.getLocation(): Location {
//    return suspendCancellableCoroutine { continuation ->
//        val locationManager = getSystemService<LocationManager>()!!
//
//        val hasFineLocationPermission = ActivityCompat.checkSelfPermission(
//            this@getLocation,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED
//        val hasCoarseLocationPermission = ActivityCompat.checkSelfPermission(
//            this@getLocation,
//            Manifest.permission.ACCESS_COARSE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED
//
//        val signal = CancellationSignal()
//        if (hasFineLocationPermission && hasCoarseLocationPermission) {
//            locationManager.getCurrentLocation(
//                LocationManager.NETWORK_PROVIDER,
//                signal,
//                mainExecutor
//            ) { location ->
//                println("Got location: $location")
//                continuation.resume(location)
//            }
//        } else {
//            continuation.resumeWithException(
//                RuntimeException("Missing location permission")
//            )
//        }
//
//        continuation.invokeOnCancellation {
//            signal.cancel()
//        }
//    }
//}