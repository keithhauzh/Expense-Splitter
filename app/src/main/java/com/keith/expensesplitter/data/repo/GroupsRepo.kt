package com.keith.expensesplitter.data.repo

import com.keith.expensesplitter.data.db.GroupsDao
import com.keith.expensesplitter.data.model.Group
import kotlinx.coroutines.flow.Flow

class GroupsRepo(
    private val dao: GroupsDao
) {
    fun makeGroup(group: Group): Long {
        return dao.makeGroup(group)
    }

    fun getAllGroups() : List<Group> {
        return dao.getAllGroups()
    }

    fun getGroupById(id: Long): Group? {
        return dao.getGroupById(id)
    }

    fun updateGroup(group: Group){
        dao.update(group)
    }

    fun deleteGroup(id: Long){
        dao.delete(id)
    }
}