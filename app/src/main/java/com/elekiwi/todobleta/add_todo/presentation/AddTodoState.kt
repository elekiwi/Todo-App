package com.elekiwi.todobleta.add_todo.presentation

data class AddTodoState(
    val todoId: Int = -1,
    val title: String = "",
    val description: String = "",
    val isDone: Boolean = false
)
