package com.elekiwi.todobleta.core.presentation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object TodoList: Screen
    @Serializable
    data object AddTodo: Screen
}