package com.elekiwi.todobleta.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntity(
    val title: String,
    val description: String?,
    val isDone: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)
