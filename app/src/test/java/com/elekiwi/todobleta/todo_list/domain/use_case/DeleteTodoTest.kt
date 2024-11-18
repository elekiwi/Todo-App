package com.elekiwi.todobleta.todo_list.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elekiwi.todobleta.core.data.repository.FakeTodoRepository
import com.elekiwi.todobleta.core.domain.model.TodoItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DeleteTodoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeTodoRepository: FakeTodoRepository
    private lateinit var deleteTodo: DeleteTodo

    @Before
    fun setup(){
        fakeTodoRepository = FakeTodoRepository()
        deleteTodo = DeleteTodo(fakeTodoRepository)
        fakeTodoRepository.shouldHaveFilledList(true)
    }

    @Test
    fun `delete todo from db, todo is deleted`() = runTest {
        val todo = TodoItem("Todo 2", "Todo 2", false)

        deleteTodo.invoke(todo)

        assertFalse(fakeTodoRepository.getAllTodos().first().contains(todo))
    }
}