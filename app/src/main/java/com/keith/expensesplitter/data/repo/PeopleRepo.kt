package com.keith.expensesplitter.data.repo

import com.keith.expensesplitter.data.db.PeopleDao
import com.keith.expensesplitter.data.model.Person
import kotlinx.coroutines.flow.Flow

class PeopleRepo (
    private val dao: PeopleDao
) {
    fun makePerson(person : Person) {
        dao.makePerson(person)
    }

    fun getPeopleByGroupId(groupId: Long) : Flow<List<Person>> {
        return dao.getPeopleByGroupId(groupId)
    }

    fun getPersonById(id : Long): Person? {
        return dao.getPersonById(id)
    }

    fun updatePerson(person: Person) {
        dao.update(person)
    }

    fun deletePerson(id: Long) {
        dao.delete(id)
    }

}