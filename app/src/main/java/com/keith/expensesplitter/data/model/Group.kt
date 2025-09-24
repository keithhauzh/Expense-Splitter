package com.keith.expensesplitter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Group (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val details: String,
)


