package com.keith.expensesplitter.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.keith.expensesplitter.MyApp
import com.keith.expensesplitter.data.model.Expense
import com.keith.expensesplitter.data.model.Group
import com.keith.expensesplitter.data.model.Person
import com.keith.expensesplitter.data.repo.ExpensesRepo
import com.keith.expensesplitter.data.repo.GroupsRepo
import com.keith.expensesplitter.data.repo.PeopleRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ActivityViewModel(
    private val groupsRepo: GroupsRepo,
    private val expensesRepo: ExpensesRepo,
    private val peopleRepo: PeopleRepo
): ViewModel() {
    private val _group = MutableStateFlow<oneGroup?>(null)
    private val _expenses = MutableStateFlow<List<oneExpense>>(emptyList())
    private val _people = MutableStateFlow<List<onePerson>>(emptyList())

    private val _error = MutableSharedFlow<String>()

    fun resetExpenseState(){
        _expenses.value = emptyList()
    }

    fun holdGroup(group: oneGroup){
        _group.value = group
    }

    fun holdExpenses(expenses: List<oneExpense>){
        _expenses.value = expenses
    }

    fun holdPeople(people: List<onePerson>){
        _people.value = people
    }

    fun complete(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val group = _group.value
                if(group!=null){
                    val groupId = groupsRepo.makeGroup(
                        Group(
                            name = group.name,
                            details = group.details,
                            totalAmount = _expenses.value.sumOf { it.amount }
                        )
                    )
                    completeExpenses(groupId)
                    completePeople(groupId)
                }
            }catch (e: Exception){
                _error.emit(e.message?: "Could not complete group")
            }
        }
    }

    private fun completeExpenses(id: Long){
        _expenses.value.forEach { expense ->
            expensesRepo.makeExpense(
                Expense(
                    name = expense.name,
                    amount = expense.amount,
                    groupId = id
                )
            )
        }
    }

    private fun completePeople(id: Long){
        _people.value.forEach { person ->
            peopleRepo
                .makePerson(
                    Person(
                        name = person.name,
                        groupId = id
                    )
                )
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider
                    .AndroidViewModelFactory
                    .APPLICATION_KEY
                ] as MyApp
                ActivityViewModel(
                    app.groupsRepo,
                    app.expensesRepo,
                    app.peopleRepo
                )
            }
        }
    }
}