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

    fun getExpensesByGroupId(groupId: Long) : List<Expense> {
        return dao.getExpenseByGroupId(groupId)
    }
}