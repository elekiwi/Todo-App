package com.elekiwi.todobleta.core.domain.model

data class TodoItem(
    val title: String,
    val description: String,
    val isDone: Boolean,

    val id: Int = 0
)
