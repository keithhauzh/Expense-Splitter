package com.keith.expensesplitter.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.keith.expensesplitter.data.model.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpensesDao {
    @Query("SELECT * FROM Expense WHERE groupId = :groupId")
    fun getExpenseByGroupId(groupId: Long): List<Expense>

    @Query("SELECT * FROM Expense WHERE id = :id")
    fun getExpenseById(id: Long): Expense?

    @Insert
    fun makeExpense(expense: Expense)

    @Update
    fun update(expense: Expense)

    @Query("DELETE FROM Expense WHERE id = :id")
    fun delete(id: Long)
}