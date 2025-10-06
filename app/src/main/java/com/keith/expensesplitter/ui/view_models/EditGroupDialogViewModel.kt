package com.keith.expensesplitter.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.keith.expensesplitter.MyApp
import com.keith.expensesplitter.data.model.Group
import com.keith.expensesplitter.data.repo.GroupsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditGroupDialogViewModel(
    private val repo: GroupsRepo
) : ViewModel() {
    private val _finish = MutableSharedFlow<Unit>()
    val finish = _finish.asSharedFlow()
    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    private var _group = MutableStateFlow(Group(
        name = "",
        details = ""
    ))
    val group = _group.asStateFlow()

    fun getGroup(id: Long) {
        viewModelScope.launch (Dispatchers.IO) {
            repo.getGroupById(id)?.let {
                _group.value = it
            }
        }
    }

    fun updateGroup(name: String, details: String) {
        try {
            require(name.isNotBlank()) {"Group name cannot be blank"}
            viewModelScope.launch(Dispatchers.IO) {
                _group.value.id?.let {
                    repo.updateGroup(
                        group = _group.value.copy(name = name, details = details)
                    )
                }
                _finish.emit(Unit)
            }
        } catch (e: Exception) {
            viewModelScope.launch { _error.emit(e.message.toString()) }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApp).groupsRepo
                EditGroupDialogViewModel(myRepository)
            }
        }
    }
}