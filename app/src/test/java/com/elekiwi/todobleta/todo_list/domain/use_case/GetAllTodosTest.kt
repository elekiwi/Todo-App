package com.elekiwi.todobleta.todo_list.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elekiwi.todobleta.core.data.repository.FakeTodoRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetAllTodosTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeTodoRepository: FakeTodoRepository
    private lateinit var getAllTodos: GetAllTodos

    @Before
    fun setup(){
        fakeTodoRepository = FakeTodoRepository()
        getAllTodos = GetAllTodos(fakeTodoRepository)

    }

    @Test
    fun `get all notes, return notes in db`() = runTest {
        fakeTodoRepository.shouldHaveFilledList(true)

        val todos = getAllTodos.invoke().first()

        assertTrue(todos.isNotEmpty())
    }

    @Test
    fun `get todos from empty list, returns empty list`() = runTest {
        fakeTodoRepository.shouldHaveFilledList(false)
        val todos = getAllTodos.invoke().first()

        assertTrue(todos.isEmpty())
    }
}