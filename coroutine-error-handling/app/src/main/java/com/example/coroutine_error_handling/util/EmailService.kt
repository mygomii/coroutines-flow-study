package com.example.coroutine_error_handling.util

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

object EmailService {
    private val mailingList = mutableListOf<String>()

    fun addToMailingList(emails: List<String>) {
        mailingList.addAll(emails)
    }

    suspend fun sendNewsletter() {
        // TODO;
//        coroutineScope {
//            mailingList.forEach { emailAddress ->
//                launch {
//                    try {
//                        sendEmail(emailAddress)
//                    } catch (e: Exception) {  // TODO; Coroutine Error Handling - homework2
//                        println("#### ${e.message}")
//                    }
//                }
//            }
//        }

        coroutineScope {
            val deferredList = mailingList.map { emailAddress ->
                async {
                    sendEmail(emailAddress)
                    emailAddress
                }
            }

            deferredList.forEach { deferred ->
                try {
                    val address = deferred.await()
                    println("### success : ${deferred.await()}")
                } catch (e: Exception) {
                    println("### failure : ${e.message}")
                }
            }
        }

        // TODO; 저번에 이렇게 했었음
//        coroutineScope {
//            mailingList.forEach { emailAddress ->
//                async {
//                    try {
//                        sendEmail(emailAddress)
//                    } catch (e: Exception) {
//                        println("#### ${e.message}")
//                    }
//                }
//            }
//        }
    }


    private suspend fun sendEmail(address: String) {
        println("Sending email to $address")
        if (!address.contains("@")) {
            throw Exception("Invalid email address: $address")
        }
        delay(Random.nextLong(1500, 4000))
        println("Email sent to $address")
    }
}