package com.keith.expensesplitter.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.keith.expensesplitter.data.model.Expense
import com.keith.expensesplitter.data.model.Group
import com.keith.expensesplitter.data.model.Person

@Database(
    entities = [
        Group::class,
        Expense::class,
        Person::class
               ],
    version=1)
abstract class MyDatabase: RoomDatabase() {
    abstract fun getGroupsDao(): GroupsDao
    abstract fun getExpensesDao(): ExpensesDao
    abstract fun getPeopleDao(): PeopleDao
    companion object {
        const val NAME = "my_database"
    }
}