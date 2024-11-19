package com.elekiwi.todobleta.add_todo.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elekiwi.todobleta.MainCoroutineRule
import com.elekiwi.todobleta.core.data.repository.FakeTodoRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UpsertTodoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeTodoRepository: FakeTodoRepository
    private lateinit var upsertTodo: UpsertTodo

    @Before
    fun setup() {
        fakeTodoRepository = FakeTodoRepository()
        upsertTodo = UpsertTodo(fakeTodoRepository)
    }


    @Test
    fun `upsert todo with empty title, return false`() = runTest {
        val isInserted = upsertTodo.invoke(
            title = "", description = "description", isDone = false
        )

        assertFalse(isInserted)
    }

    @Test
    fun `upsert valid todo, return true`() = runTest {
        val isInserted = upsertTodo.invoke(
            title = "title", description = "description", isDone = false
        )

        assertTrue(isInserted)
    }
}