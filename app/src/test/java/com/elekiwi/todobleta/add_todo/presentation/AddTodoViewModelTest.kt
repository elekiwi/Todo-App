package com.elekiwi.todobleta.add_todo.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elekiwi.todobleta.MainCoroutineRule
import com.elekiwi.todobleta.add_todo.domain.use_case.UpsertTodo
import com.elekiwi.todobleta.core.data.repository.FakeTodoRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class AddTodoViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeTodoRepository: FakeTodoRepository
    private lateinit var addTodoViewModel: AddTodoViewModel

    @Before
    fun setup() {
        fakeTodoRepository = FakeTodoRepository()
        val upsertTodo = UpsertTodo(fakeTodoRepository)
        addTodoViewModel = AddTodoViewModel(upsertTodo)
    }

    @Test
    fun `upsert todo with empty title, return false`() = runTest {
        val isInserted = addTodoViewModel.upsertTodo(
            title = "", description = "description", isDone = false
        )

        assertFalse(isInserted)
    }

    @Test
    fun `upsert valid todo, return true`() = runTest {
        val isInserted = addTodoViewModel.upsertTodo(
            title = "title", description = "description", isDone = false
        )

        assertTrue(isInserted)
    }
}