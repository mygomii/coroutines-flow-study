package com.example.coroutine_cancellation.util

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay

object RemoteService {
    suspend fun uploadFile() {
        val chunks = List(400) { it }
        println("Uploading File")
        var index = 0
        while (index < chunks.size) {
            try {
                // Simulate uploading a single chunk of data
                delay(5)
                index++
                println("Progress: ${index * 100 / chunks.size}%")
            } catch (e: Exception) { // TODO; Coroutine Cancellation - homework -1
                if (e is CancellationException) throw e
                println("Error uploading file: ${e.message}")
            } finally {
                println("finally")
            }
        }
        println("Upload Complete")
    }
}