package com.keith.expensesplitter.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class oneExpense(val name: String, val amount: Long)
class MakeExpenseViewModel: ViewModel() {
    private val _expenses = MutableStateFlow<List<oneExpense>>(emptyList())
    val expenses = _expenses.asStateFlow()
    private val _finish = MutableSharedFlow<List<oneExpense>>()
    val finish = _finish.asSharedFlow()
    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    fun makeExpense(name: String, amount: Long) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                val oneExpense = oneExpense(name, amount)
                _expenses.update { currentExpenses ->
                    currentExpenses + oneExpense
                }
            } catch (e: Exception) {
                _error.emit(e.message?: "Could not make expense")
            }
        }
    }

    fun resetExpenses(){
        _expenses.value = emptyList()
        viewModelScope.launch {
            _error.emit("")
        }
    }
}