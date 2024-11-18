package com.elekiwi.todobleta.core.domain.repository

import com.elekiwi.todobleta.core.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun upsertTodo(todoItem: TodoItem)
    suspend fun deleteTodo(todoItem: TodoItem)
    suspend fun getTodoById(id: Int): TodoItem?
    fun getAllTodos(): Flow<List<TodoItem>>

}