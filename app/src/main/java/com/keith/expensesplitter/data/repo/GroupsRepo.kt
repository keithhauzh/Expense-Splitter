package com.keith.expensesplitter.data.repo

import com.keith.expensesplitter.data.db.GroupsDao
import com.keith.expensesplitter.data.model.Group
import kotlinx.coroutines.flow.Flow

class GroupsRepo(
    private val dao: GroupsDao
) {
    fun add(group: Group){
        dao.addGroup(group)
    }

    fun getAllGroups() : Flow<List<Group>> {
        return dao.getAllGroups()
    }

    fun getGroupById(id:Int): Group? {
        return dao.getGroupById(id)
    }

    fun updateGroup(group: Group){
        dao.update(group)
    }

    fun deleteGroup(id: Int){
        dao.delete(id)
    }
}