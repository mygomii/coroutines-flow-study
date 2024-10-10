package com.example.coroutine_error_handling.util

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlin.random.Random

object EmailService {
    private val mailingList = mutableListOf<String>()

    fun addToMailingList(emails: List<String>) {
        mailingList.addAll(emails)
    }

    suspend fun sendNewsletter() {
        coroutineScope {
            val deferredList = mailingList.map { emailAddress ->
                async {
                    sendEmail(emailAddress)
                    emailAddress // 결과로 이메일 주소 반환
                }
            }

            deferredList.forEach { deferred ->
                try {
                    val address = deferred.await()
                    println("### Successfully sent email to $address")
                } catch (e: Exception) {
                    println("### Failed to send email: ${e.message}")
                }
            }
        }
    }

//    suspend fun sendNewsletter() {
//        coroutineScope {
//            mailingList.forEach { emailAddress ->
//                launch {
//                    try {
//                        sendEmail(emailAddress)
//                    } catch (e: Exception) {  // TODO; Coroutine Error Handling - homework2
//                        println("### ${e.message}")
//                    }
//                }
//            }
//        }
//    }

    private suspend fun sendEmail(address: String) {
        println("### Sending email to $address")
        if (!address.contains("@")) {
            throw Exception("### Invalid email address: $address")
        }
        delay(Random.nextLong(1500, 4000))
        println("### Email sent to $address")
    }
}