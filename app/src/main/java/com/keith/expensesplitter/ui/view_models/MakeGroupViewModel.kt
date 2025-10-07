package com.keith.expensesplitter.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class oneGroup(val name: String, val details: String)
class MakeGroupViewModel: ViewModel() {
    private val _group = MutableStateFlow<oneGroup?>(null)
    val group = _group.asStateFlow()
    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()
    fun makeGroup(name:String, details: String){
        viewModelScope.launch (Dispatchers.IO) {
            try {
                require(name.isNotBlank()){"Name cannot be blank"}
                val group = oneGroup(name=name, details=details)
                _group.emit(group)
            } catch (e: Exception){
                _error.emit(e.message?: "Could not make group")
            }
        }
    }

    fun resetGroup(){
        _group.value = null
        viewModelScope.launch {
            _error.emit("")
        }
    }
}