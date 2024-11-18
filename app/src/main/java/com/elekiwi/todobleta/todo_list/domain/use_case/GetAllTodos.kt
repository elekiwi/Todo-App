package com.elekiwi.todobleta.todo_list.domain.use_case

import com.elekiwi.todobleta.core.domain.model.TodoItem
import com.elekiwi.todobleta.core.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class GetAllTodos(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(): Flow<List<TodoItem>> {
        return todoRepository.getAllTodos()
    }
}