package com.mygomii.coroutine_synchronization.homework

/**
 * Listener definition to use in your class
 */
fun interface LeaderboardListener {
    fun onLeaderboardUpdated(leaderboard: String)
}

private val listeners = mutableListOf<LeaderboardListener>()


/**
 * Logic to calculate the top 3 highest scores from a map
 */
val allScores = mutableMapOf<String, Int>(
    "Mani" to 45,
    "Alex" to 100,
    "Peter" to 300,
)

val topThree = allScores
    .entries
    .sortedByDescending { it.value }
    .take(3)
    .withIndex()
    .joinToString("\n") { (index, entry) ->
        "### ${index + 1} is ${entry.key} with ${entry.value} points"
    }


class Leaderboard {
    private val lock = Any()

    fun addScore(name: String, score: Int) {
        val topThree: String
        val listeners2: List<LeaderboardListener>

        synchronized(lock) {
            val tempScore = allScores[name]
            if (tempScore == null || score > tempScore) {
                allScores[name] = score
            }

            topThree = allScores
                .entries
                .sortedByDescending { it.value }
                .take(3)
                .withIndex()
                .joinToString("\n") { (index, entry) ->
                    "### ${index + 1} is ${entry.key} with ${entry.value} points"
                }

            listeners2 = listeners.toList()

            for (listener in listeners2) {
                listener.onLeaderboardUpdated(topThree)
            }
        }


    }

    fun registerListener(listener: LeaderboardListener) {
//        synchronized(lock) {
            listeners.add(listener)
//        }
    }

    fun unregisterListener(listener: LeaderboardListener) {
//        synchronized(lock) {
            listeners.remove(listener)
//        }
    }
}