package com.keith.expensesplitter.ui.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.keith.expensesplitter.MyApp
import com.keith.expensesplitter.data.model.Expense
import com.keith.expensesplitter.data.repo.ExpensesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MakeExpenseViewModel (
    private val repo: ExpensesRepo
): ViewModel() {
    private val _finish = MutableSharedFlow<Unit>()
    val finish = _finish.asSharedFlow()
    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    fun makeExpense(expense: Expense) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                repo.makeExpense(expense)
                _finish.emit(Unit)
            } catch (e: Exception) {
                _error.emit(e.message?: "Could not make expense")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository =
                    (this
                        [ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY]
                            as MyApp).expensesRepo
                MakeExpenseViewModel(myRepository)
            }
        }
    }
}