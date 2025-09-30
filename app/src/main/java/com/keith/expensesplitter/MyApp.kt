package com.keith.expensesplitter

import com.keith.expensesplitter.data.repo.ExpensesRepo
import com.keith.expensesplitter.data.repo.GroupsRepo

class MyApp {
    lateinit var groupsRepo: GroupsRepo
    lateinit var expensesRepo: ExpensesRepo
}