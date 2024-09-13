package org.example

import kotlinx.coroutines.*

fun main(args: Array<String>) {
//    easy()
    medium()
}


/*
과제 #1

매일 아침 새소리에 잠에서 깨어납니다.
시간이 지남에 따라 각각 다른 속도로 반복되는 세 가지 독특한 새소리를 발견했습니다.
한 마리의 새는 매초마다, 다른 한 마리는 2초마다, 마지막 새는 3초마다 소리를 냅니다.

지침들

각 새의 코루틴 하나를 사용하여 각 새의 소리 타이밍을 재현합니다. 각 코루틴은 완성하기 전에 네 번만 인쇄해야 합니다.
첫 번째 새는 "Coo" 소리를 냅니다.
두 번째 새는 "Caw" 소리를 냅니다.
마지막 새는 "Chirp" 소리를 냅니다.

*/

fun easy() = runBlocking {
    val jobs = getList()

    jobs.forEach { it.join() }
}


/*
#과제2
새들에게 깨어난 후에는 한동안 새들의 음악을 즐겨 듣습니다.
그 후에는 하루를 준비해야 하므로 새들의 음악을 잠시 듣고 나면 창문을 닫고 준비합니다.

지침들
콘솔에 네 번만 인쇄할 수 있다는 제한을 제거하여 이전 할당을 연장하고,
이제 각 코루틴을 무기한 인쇄할 수 있습니다. 10초 후에 실행 중인 모든 코루틴을 취소하는 메커니즘을 추가합니다.

*/

fun medium() = runBlocking {
    val jobs = getList()
    delay(10000)

    jobs.forEach {
        it.cancel()
    }
}


fun getList() = runBlocking {
    return@runBlocking listOf(
        launch { birdOne() },
        launch { birdTwo() },
        launch { birdThree() }
    )
}

suspend fun birdOne() {
    repeat(4) {
        println("Coo")
        delay(1000)
    }
}

suspend fun birdTwo() {
    repeat(4) {
        println("Caw")
        delay(2000)
    }
}

suspend fun birdThree() {
    repeat(4) {
        println("Chirp")
        delay(3000)
    }
}
