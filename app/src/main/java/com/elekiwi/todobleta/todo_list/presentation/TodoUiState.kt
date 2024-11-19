package com.elekiwi.todobleta.todo_list.presentation

import com.elekiwi.todobleta.core.domain.model.TodoItem

sealed interface TodoUiState {
    object Loading : TodoUiState
    data class Error(val throwable: Throwable) : TodoUiState
    data class Success(val todos: List<TodoItem>) : TodoUiState
}