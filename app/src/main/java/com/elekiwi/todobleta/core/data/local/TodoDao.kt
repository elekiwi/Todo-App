package com.elekiwi.todobleta.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Upsert
    fun upsertTodo(todoEntity: TodoEntity)

    @Delete
    fun deleteTodo(todoEntity: TodoEntity)

    @Query("SELECT * FROM todoentity WHERE id = :id")
    fun getTodoById(id: Int): TodoEntity

    @Query("SELECT * FROM todoentity")
    fun getAllTodos(): Flow<List<TodoEntity>>
}