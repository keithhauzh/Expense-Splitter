package com.keith.expensesplitter.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.keith.expensesplitter.data.model.Group

@Database(
    entities = [Group::class],
    version=1)
abstract class MyDatabase: RoomDatabase() {
    abstract fun getGroupsDao(): GroupsDao
    companion object {
        const val NAME = "my_database"
    }
}