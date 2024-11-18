package com.elekiwi.todobleta.todo_list.domain.use_case

import com.elekiwi.todobleta.core.domain.model.TodoItem
import com.elekiwi.todobleta.core.domain.repository.TodoRepository

class DeleteTodo(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(todoItem: TodoItem) {
        todoRepository.deleteTodo(todoItem)
    }
}