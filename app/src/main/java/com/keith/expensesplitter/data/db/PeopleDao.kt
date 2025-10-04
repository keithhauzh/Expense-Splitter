package com.keith.expensesplitter.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.keith.expensesplitter.data.model.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PeopleDao {
    @Query("SELECT * FROM Person WHERE groupId = :groupId")
    fun getPeopleByGroupId(groupId: Long): Flow<List<Person>>

    @Query("SELECT * FROM Person WHERE id = :id")
    fun getPersonById(id: Long): Person?

    @Insert
    fun makePerson(person: Person)

    @Update
    fun update(person: Person)

    @Query("DELETE FROM Expense WHERE id = :id")
    fun delete(id: Long)
}