package com.keith.expensesplitter.ui.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.keith.expensesplitter.MyApp
import com.keith.expensesplitter.data.model.Expense
import com.keith.expensesplitter.data.model.Group
import com.keith.expensesplitter.data.model.Person
import com.keith.expensesplitter.data.repo.ExpensesRepo
import com.keith.expensesplitter.data.repo.GroupsRepo
import com.keith.expensesplitter.data.repo.PeopleRepo
import com.keith.expensesplitter.ui.fragments.PreviousGroupsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.roundToInt

class DisplayGroupViewModel(
    private val groupsRepo: GroupsRepo,
    private val expensesRepo: ExpensesRepo,
    private val peopleRepo: PeopleRepo
) : ViewModel() {
    private val _group = MutableStateFlow<Group?>(null)
    val group: StateFlow<Group?> = _group
    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses
    private val _people = MutableStateFlow<List<Person>>(emptyList())
    val people: StateFlow<List<Person>> = _people
    private val _numberOfPeople = MutableStateFlow<Int>(0)
    val numberOfPeople = _numberOfPeople.asStateFlow()
    private val _finish = MutableSharedFlow<Unit>()
    val finish: SharedFlow<Unit> = _finish
    private val _totalAmount = MutableStateFlow<Double>(0.0)
    val totalAmount: StateFlow<Double> = _totalAmount
    private val _amountPerPerson = MutableStateFlow<Double>(0.0)
    val amountPerPerson: StateFlow<Double> = _amountPerPerson

    fun loadGroup(id: Long){
        viewModelScope.launch (Dispatchers.IO) {
            groupsRepo.getGroupById(id)?.let {
                _group.value = it
            }
        }
    }

    fun loadExpenses(id: Long){
        viewModelScope.launch (Dispatchers.IO) {
            expensesRepo.getExpensesByGroupId(id).let {
                _expenses.value = it
            }
        }
    }

    fun loadPeople(id: Long){
        viewModelScope.launch (Dispatchers.IO) {
            peopleRepo.getPeopleByGroupId(id).let {
                _people.value = it
                _numberOfPeople.value = it.size
                Log.d("numberOfPeople", _numberOfPeople.value.toString())
            }
        }
    }

    fun calculateExpenses(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            peopleRepo.getPeopleByGroupId(id).let { people ->
                val peopleSize = people.size
                expensesRepo.getExpensesByGroupId(id).let { expenses ->
                    val totalAmount = expenses.sumOf { it.amount.toDouble() }
                    _totalAmount.value = totalAmount
                    if(peopleSize==0){
                        _amountPerPerson.value = totalAmount
                    }else{
                        _amountPerPerson.value = BigDecimal(totalAmount).divide(BigDecimal(peopleSize), 2,
                            RoundingMode.HALF_UP).toDouble()
                    }
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as MyApp
                DisplayGroupViewModel(
                    groupsRepo = app.groupsRepo,
                    expensesRepo = app.expensesRepo,
                    peopleRepo = app.peopleRepo
                )
            }
        }
    }
}