package com.elekiwi.todobleta.core.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.elekiwi.todobleta.core.di.AppModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@SmallTest
@UninstallModules(AppModule::class)
class TodoDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    //To make sure task are executed independently with corroutines and avoid blocking the thread.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    //To inject the Db it needs to be public.
    @Inject
    lateinit var todoDb: TodoDatabase
    private lateinit var todoDao: TodoDao

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        hiltRule.inject()
        todoDao = todoDb.todoDao
    }

    @After
    fun tearDown(){
        todoDb.close()
    }

    @Test
    fun getAllTodosFromEmptyDb_returnsEmptyList() = runTest {
        val flow = todoDao.getAllTodos()

        val result = flow.first()
        assertTrue(result.isEmpty())
    }

    @Test
    fun getAllTodosFromDb_todoListIsNotEmpty() = runTest {
        val todo1 = TodoEntity(id = 1, title = "Test 1", description = "", isDone = false)
        val todo2 = TodoEntity(id = 2, title = "Test 2", description = "", isDone = true)
        todoDao.upsertTodo(todo1)
        todoDao.upsertTodo(todo2)

        val result = todoDao.getAllTodos().first()

        assertEquals(2, result.size)
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun upsertTodo_newTodoIsInserted() = runTest {
        val todo1 = TodoEntity(id = 1, title = "Test 1", description = "", isDone = false)
        todoDao.upsertTodo(todo1)

        val result = todoDao.getAllTodos().first()

        assertTrue(
            result.contains(todo1)
        )
    }

    @Test
    fun deleteTodo_todoIsDeleted() = runTest {
        val todo1 = TodoEntity(id = 1, title = "Test 1", description = "", isDone = false)
        todoDao.upsertTodo(todo1)

        var result = todoDao.getAllTodos().first()

        assertTrue(
            result.contains(todo1)
        )

        todoDao.deleteTodo(todo1)

        result = todoDao.getAllTodos().first()

        assertFalse(
            result.contains(todo1)
        )

    }

}






