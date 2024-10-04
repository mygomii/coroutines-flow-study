package com.example.coroutine_cancellation.util.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(
    val name: String,
    @PrimaryKey val id: Int = 0
)
