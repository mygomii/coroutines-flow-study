package com.example.coroutine_contexts

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun Main() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()

    ) {
        RotatingBox()

        Spacer(Modifier.height(80.dp))

        PickerScreen()
    }
}

@Composable
fun RotatingBox() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val animatedAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000)
        ),
        label = ""
    )

    Box(
        modifier = Modifier
            .size(100.dp)
            .graphicsLayer { rotationZ = 360f * animatedAngle }
            .background(Color.Blue)

    )
}

@Composable
fun PickerScreen() {
    var backgroundColor by remember { mutableStateOf(Color.White) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val findDominantColor: (Bitmap) -> Color = { bitmap ->
        val palette = Palette.from(bitmap).generate()
        val dominantColor = palette.getDominantColor(0xFFFFFF)
        Color(dominantColor)
    }

    LaunchedEffect(imageUri) {
        imageUri?.let {
            val bitmap = context.contentResolver.openInputStream(it).use {
                BitmapFactory.decodeStream(it)
            }

            backgroundColor = findDominantColor(bitmap)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text("이미지 선택")
        }

        if (imageUri != null) {
            val painter = rememberAsyncImagePainter(
                ImageRequest
                    .Builder(context)
                    .data(data = imageUri)
                    .build()
            )

            Image(
                painter = painter,
                contentDescription = null
            )
        }
    }
}