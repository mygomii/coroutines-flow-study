package com.mygomii.coroutine_synchronization

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import com.mygomii.coroutine_synchronization.homework.Leaderboard
import com.mygomii.coroutine_synchronization.homework.LeaderboardListener
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        // USE THIS TO RUN YOUR Leaderboard CLASS
//        val leaderboard = Leaderboard()
//
//
//        leaderboard.addListener { topScores: String ->
//            println("New Top Scores:")
//            println(topScores + "\n\n")
//        }
//
//        GlobalScope.launch {
//            (1..5_000).map { index ->
//                launch {
//                    val playerName = "Player $index"
//                    val playerScore = Random.nextInt(1, 10_0000)
//                    leaderboard.updateScore(playerName, playerScore)
//                }
//            }.joinAll()
//            println("Completed!")
////        }

        // TODO;
        val leaderboard = Leaderboard()

        val listener = LeaderboardListener { updatedLeaderboard ->
            println("### $updatedLeaderboard")
        }

        leaderboard.registerListener(listener)

        leaderboard.addScore("Alice", 150)
        leaderboard.addScore("mygomii", 400)
        leaderboard.addScore("mijeong", 580)
        leaderboard.addScore("Alice", 220)

        leaderboard.unregisterListener(listener)
    }
}



