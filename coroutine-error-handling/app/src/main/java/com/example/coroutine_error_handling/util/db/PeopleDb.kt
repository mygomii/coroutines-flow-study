package com.example.coroutine_error_handling.util.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coroutine_error_handling.util.db.PeopleDao
import com.example.coroutine_error_handling.util.db.Person

@Database(
    entities = [Person::class],
    version = 1
)
abstract class PeopleDb: RoomDatabase() {

    abstract val peopleDao: PeopleDao
}