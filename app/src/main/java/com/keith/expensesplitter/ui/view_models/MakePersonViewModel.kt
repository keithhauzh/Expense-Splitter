package com.keith.expensesplitter.ui.view_models

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class onePerson(val name: String)
class MakePersonViewModel (
    private val repo: PeopleRepo
): ViewModel() {
    private val _people = MutableStateFlow<List<onePerson>>(emptyList())
    val people = _people.asStateFlow()
    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    fun makePerson(person: Person){
        viewModelScope.launch (Dispatchers.IO) {
            try {
                val onePerson = onePerson(person.name)
                _people.update { currentPeople->
                    currentPeople + onePerson
                }
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