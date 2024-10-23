package com.example.flow_fundamentals

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.lifecycleScope
import com.example.flow_fundamentals.ui.theme.FlowfundamentalsTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            0
        )

        val observer = LocationObserver(applicationContext)

        observer.observeLocation(1000L).onEach {
            println("Tracked location: $it")
        }.launchIn(lifecycleScope)

        setContent {
//            CoroutinesMasterclassTheme {
//            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.R)
suspend fun Context.getLocation(): Location {
    return suspendCancellableCoroutine { continuation ->
        val locationManager = getSystemService<LocationManager>()!!

        val hasFineLocationPermission = ActivityCompat.checkSelfPermission(
            this@getLocation,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasCoarseLocationPermission = ActivityCompat.checkSelfPermission(
            this@getLocation,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val signal = CancellationSignal()
        if(hasFineLocationPermission && hasCoarseLocationPermission) {
            locationManager.getCurrentLocation(
                LocationManager.NETWORK_PROVIDER,
                signal,
                mainExecutor
            ) { location ->
                println("Got location: $location")
                continuation.resume(location)
            }
        } else {
            continuation.resumeWithException(
                RuntimeException("Missing location permission")
            )
        }

        continuation.invokeOnCancellation {
            signal.cancel()
        }
    }
}