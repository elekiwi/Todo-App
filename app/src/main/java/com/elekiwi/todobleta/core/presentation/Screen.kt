package com.elekiwi.todobleta.core.presentation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object TodoList: Screen
    @Serializable
    data class AddTodo(val todoId: Int = -1): Screen
}