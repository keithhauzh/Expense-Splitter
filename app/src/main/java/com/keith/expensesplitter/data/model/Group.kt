package com.keith.expensesplitter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Group (
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val details: String? = "No Details",
)



