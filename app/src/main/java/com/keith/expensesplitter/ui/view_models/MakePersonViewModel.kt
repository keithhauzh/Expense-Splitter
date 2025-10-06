package com.keith.expensesplitter.ui.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.keith.expensesplitter.MyApp
import com.keith.expensesplitter.data.model.Person
import com.keith.expensesplitter.data.repo.PeopleRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MakePersonViewModel (
    private val repo: PeopleRepo
): ViewModel() {
    private val _finish = MutableSharedFlow<Unit>()
    val finish = _finish.asSharedFlow()
    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    fun makePerson(person: Person){
        viewModelScope.launch (Dispatchers.IO) {
            try {
                repo.makePerson(person)
                _finish.emit(Unit)
            } catch (e: Exception) {
                _error.emit(e.message?: "Could not make person")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository =
                    (this
                           [ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY]
                            as MyApp).peopleRepo
                MakePersonViewModel(myRepository)
            }
        }
    }
}