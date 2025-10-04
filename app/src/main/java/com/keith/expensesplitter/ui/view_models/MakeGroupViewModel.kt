package com.keith.expensesplitter.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.keith.expensesplitter.MyApp
import com.keith.expensesplitter.data.model.Group
import com.keith.expensesplitter.data.repo.GroupsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MakeGroupViewModel(
    private val repo: GroupsRepo
): ViewModel() {
    private val _finish = MutableSharedFlow<Long>()
    val finish = _finish.asSharedFlow()
    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()
    fun addGroup(name: String, details: String) {
            viewModelScope.launch (Dispatchers.IO) {
                try {
                    require(name.isNotBlank()) {"Name cannot be blank"}
                    val group = Group(name = name, details = details)
                    val groupId = repo.makeGroup(group)
                    _finish.emit(groupId)
                } catch (e: Exception) {
                    _error.emit(e.message?: "Could not make group")
                }
            }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository =
                    (this
                        [ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY]
                            as MyApp).groupsRepo
                MakeGroupViewModel(myRepository)
            }
        }
    }
}