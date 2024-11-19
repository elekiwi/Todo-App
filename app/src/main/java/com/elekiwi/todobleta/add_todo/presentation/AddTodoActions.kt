package com.elekiwi.todobleta.add_todo.presentation

sealed interface AddTodoActions {
    data class UpdateTitle(val newTitle: String) : AddTodoActions
    data class UpdateDescription(val newDescription: String) : AddTodoActions
    data object SaveTodo: AddTodoActions
    data class LoadTodo(val id: Int): AddTodoActions

}