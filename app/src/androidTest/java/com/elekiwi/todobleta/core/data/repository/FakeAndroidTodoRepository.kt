package com.elekiwi.todobleta.core.data.repository

import com.elekiwi.todobleta.core.domain.model.TodoItem
import com.elekiwi.todobleta.core.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAndroidTodoRepository: TodoRepository {

    private var todoItems = mutableListOf<TodoItem>()

    fun shouldHaveFilledList(shouldHaveFilledList: Boolean) {
        todoItems =
        if (shouldHaveFilledList) {
             mutableListOf(
                TodoItem("Todo 1", "Todo 1", false),
                TodoItem("Todo 2", "Todo 2", false),
                TodoItem("Todo 3", "Todo 3", false),
                TodoItem("Todo 4", "Todo 4", false)
            )
        } else {
            mutableListOf()
        }
    }

    override suspend fun upsertTodo(todoItem: TodoItem) {
        todoItems.add(todoItem)
    }

    override suspend fun deleteTodo(todoItem: TodoItem) {
        todoItems.remove(todoItem)
    }

    override suspend fun getTodoById(id: Int): TodoItem? {
        return todoItems.find { it.id == id }
    }

    override fun getAllTodos(): Flow<List<TodoItem>> {
        return flow {
            emit(todoItems)
        }
    }

}