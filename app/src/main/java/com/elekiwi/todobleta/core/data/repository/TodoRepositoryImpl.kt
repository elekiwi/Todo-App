package com.elekiwi.todobleta.core.data.repository

import com.elekiwi.todobleta.core.data.local.TodoDatabase
import com.elekiwi.todobleta.core.data.mapper.toEditedTodoEntity
import com.elekiwi.todobleta.core.data.mapper.toTodoEntityForDelete
import com.elekiwi.todobleta.core.data.mapper.toTodoEntityForInsert
import com.elekiwi.todobleta.core.data.mapper.toTodoItem
import com.elekiwi.todobleta.core.domain.model.TodoItem
import com.elekiwi.todobleta.core.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    todoDb: TodoDatabase
): TodoRepository {

    private val todoDao = todoDb.todoDao

    override suspend fun upsertTodo(todoItem: TodoItem) {
        if (todoItem.id == -1){
            todoDao.upsertTodo(todoItem.toTodoEntityForInsert())
        } else{
            todoDao.upsertTodo(todoItem.toEditedTodoEntity())
        }
    }


    override suspend fun deleteTodo(todoItem: TodoItem) {
       todoDao.deleteTodo(todoItem.toTodoEntityForDelete())
    }

    override suspend fun getTodoById(id: Int): TodoItem? {
        return todoDao.getTodoById(id).toTodoItem()
    }

    override fun getAllTodos(): Flow<List<TodoItem>> {
        return todoDao.getAllTodos().map {
            items ->
            items.map { it.toTodoItem() }
        }
    }
}