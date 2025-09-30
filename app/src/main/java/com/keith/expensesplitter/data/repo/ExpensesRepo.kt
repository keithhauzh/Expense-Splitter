package com.keith.expensesplitter.data.repo

import com.keith.expensesplitter.data.db.ExpensesDao
import com.keith.expensesplitter.data.model.Expense
import kotlinx.coroutines.flow.Flow

class ExpensesRepo (
    private val dao: ExpensesDao
) {
    fun makeExpense(expense: Expense) {
        dao.makeExpense(expense)
    }

    fun getAllExpenses() : Flow<List<Expense>> {
        return dao.getAllExpenses()
    }

    fun getExpensesById(id:Int): Expense? {
        return dao.getExpenseById(id)
    }

    fun updateExpense(expense: Expense) {
        dao.update(expense)
    }

    fun deleteExpense(id: Int) {
        dao.delete(id)
    }
}