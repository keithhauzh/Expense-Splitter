package com.keith.expensesplitter.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.keith.expensesplitter.data.model.Group
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupsDao {
    @Query("SELECT * FROM `Group`")
    fun getAllGroups(): List<Group>

    @Query("SELECT * FROM `Group` WHERE id = :id")
    fun getGroupById(id:Long): Group?

    @Insert
    fun makeGroup(group: Group): Long

    @Update
    fun update(group: Group)

    @Query("DELETE FROM `Group` WHERE id = :id")
    fun delete(id: Long)
}