package com.example.coroutine_synchronization

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

fun synchronizationDemo() {
    var count = 0
    val lock = Any()
    val mutex = Mutex()
    GlobalScope.launch {
        (1..100_000).map {
            launch {
//                count = count + 1
//                synchronized(lock) {
//                    count++
//                }
                mutex.lock()
                count++
                mutex.unlock()
//                mutex.withLock {
//                    count++
//                }dsdsdsdhk

            }
        }.joinAll()

        println("The count is $count")
    }
}