package com.keith.expensesplitter.ui.view_models

import android.util.Log
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
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class onePerson(val name: String)

class GroupCreationViewModel(
    private val groupsRepo: GroupsRepo,
    private val expensesRepo: ExpensesRepo,
    private val peopleRepo: PeopleRepo
): ViewModel() {

    private val _finish = MutableSharedFlow<Unit>()
    val finish = _finish.asSharedFlow()
    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    fun makeGroup(name:String, details: String){
        viewModelScope.launch (Dispatchers.IO) {
            try {
                require(name.isNotBlank()){"Name cannot be blank"}
                val group = oneGroup(name=name, details=details)
            } catch (e: Exception){
                _error.emit(e.message?: "Could not make group")
            }
            false
        }
    }

    fun makeExpense(name: String, amount: Long){
        viewModelScope.launch (Dispatchers.IO){
            try {
                _expenses.update{ expenses->
                    expenses + oneExpense(name, amount)
                }
            } catch (e: Exception){
                _error.emit(e.message?: "Could not add expense")
            }
        }
    }

    fun makePerson(name: String){
        viewModelScope.launch (Dispatchers.IO){
            try {
                _people.update { people->
                    people + onePerson(name)
                }
            }catch (e: Exception){
                _error.emit(e.message?: "Could not add person")
            }
        }
    }

    fun complete() {
        viewModelScope.launch (Dispatchers.IO){
            try {
                Log.d("value",_group.value.toString())
                val doneGroup = _group.value
                if(doneGroup!=null){
                    val groupId = groupsRepo.makeGroup(
                        Group(
                            name=doneGroup.name,
                            details=doneGroup.details
                        )
                    )
                    Log.d("groupId", groupId.toString())
                    completeExpenses(groupId)
                    completePeople(groupId)
                }else{
                    _error
                        .emit(
                            "No group created yet. Please create a group first..."
                        )
                }
            }catch (e: Exception){
                _error.emit(e.message?:"Could not complete group creation")
            }
        }
    }

    private fun completeExpenses(id: Long){
        _expenses.value.forEach { expense ->
            Log.d(
                "expense-object"
                    ,
                Expense(
                    name = expense.name,
                    amount = expense.amount,
                    groupId = id
                ).toString()
            )

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
        _people.value.forEach {person ->
            Log.d(
                "people-object",
                Person(
                    name = person.name,
                    groupId = id
                ).toString()
            )
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
                    .APPLICATION_KEY] as MyApp
                GroupCreationViewModel(
                    app.groupsRepo,
                    app.expensesRepo,
                    app.peopleRepo
                )
            }
        }
    }
}

