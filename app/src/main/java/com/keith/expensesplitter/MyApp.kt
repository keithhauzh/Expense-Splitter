package com.keith.expensesplitter

import android.app.Application
import androidx.room.Room
import com.google.android.material.color.DynamicColors
import com.google.android.material.color.utilities.DynamicColor
import com.keith.expensesplitter.data.db.MyDatabase
import com.keith.expensesplitter.data.model.Group
import com.keith.expensesplitter.data.repo.ExpensesRepo
import com.keith.expensesplitter.data.repo.GroupsRepo
import com.keith.expensesplitter.data.repo.PeopleRepo

class MyApp: Application() {
    lateinit var groupsRepo: GroupsRepo
    lateinit var expensesRepo: ExpensesRepo
    lateinit var peopleRepo: PeopleRepo

    override fun onCreate() {
        super.onCreate()
//        deleteDatabase(MyDatabase.NAME)

        DynamicColors.applyToActivitiesIfAvailable(this)

        val db = Room.databaseBuilder(
            this,
            MyDatabase::class.java,
            MyDatabase.NAME
        ).build()

        groupsRepo = GroupsRepo(db.getGroupsDao())
        expensesRepo = ExpensesRepo(db.getExpensesDao())
        peopleRepo = PeopleRepo(db.getPeopleDao())
    }

}