package com.elekiwi.todobleta.add_todo.domain.use_case

import com.elekiwi.todobleta.core.domain.model.TodoItem
import com.elekiwi.todobleta.core.domain.repository.TodoRepository

class UpsertTodo(
    private val todoRepository: TodoRepository
) {

    suspend operator fun invoke(
        title: String,
        description: String,
        isDone: Boolean
    ): Boolean{
        if (title.isEmpty()) {
            return false
        }

        val todo = TodoItem(title, description, isDone)
        todoRepository.upsertTodo(todo)

        return true

    }
}