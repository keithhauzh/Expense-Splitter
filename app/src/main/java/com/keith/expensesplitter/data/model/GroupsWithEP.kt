package com.keith.expensesplitter.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class GroupsWithEP (
    @Embedded val group: Group,
    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    ) val expenses: List<Expense>,
    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    ) val people: List<Person>,
)

