package com.keith.expensesplitter.ui.fragments

import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.keith.expensesplitter.data.model.Group
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.keith.expensesplitter.MyApp
import com.keith.expensesplitter.data.repo.GroupsRepo
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PreviousGroupsViewModel (
    private val repo: GroupsRepo,
) : ViewModel() {
    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups = _groups.asStateFlow()

    fun getGroups() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllGroups().let { groups ->
                _groups.value = groups
            }
        }
    }

    fun deleteGroup(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteGroup(id)
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApp).groupsRepo
                PreviousGroupsViewModel(myRepository)
            }
        }
    }
 }