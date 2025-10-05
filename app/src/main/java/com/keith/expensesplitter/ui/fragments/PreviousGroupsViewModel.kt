package com.keith.expensesplitter.ui.fragments

import android.util.Log
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
    private val groupsRepo: GroupsRepo,
) : ViewModel() {
    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups = _groups.asStateFlow()

    fun getGroups() {
        viewModelScope.launch(Dispatchers.IO) {
            groupsRepo.getAllGroups()?.let { groups ->
                Log.d("groups-data", groups.toString())
                _groups.value = groups
            }
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