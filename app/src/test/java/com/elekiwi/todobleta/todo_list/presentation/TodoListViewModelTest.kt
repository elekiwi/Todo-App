package com.elekiwi.todobleta.todo_list.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elekiwi.todobleta.MainCoroutineRule
import com.elekiwi.todobleta.core.data.repository.FakeTodoRepository
import com.elekiwi.todobleta.core.domain.model.TodoItem
import com.elekiwi.todobleta.todo_list.domain.use_case.DeleteTodo
import com.elekiwi.todobleta.todo_list.domain.use_case.GetAllTodos
import com.elekiwi.todobleta.todo_list.domain.use_case.UpdateTodo
import com.elekiwi.todobleta.todo_list.presentation.TodoUiState.Success
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TodoListViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var fakeTodoRepository: FakeTodoRepository
    private lateinit var getAllTodos: GetAllTodos
    private lateinit var deleteTodo: DeleteTodo
    private lateinit var updateTodo: UpdateTodo

    private lateinit var todoListViewModel: TodoListViewModel

    @Before
    fun setup() {
        fakeTodoRepository = FakeTodoRepository()
        getAllTodos = GetAllTodos(fakeTodoRepository)
        deleteTodo = DeleteTodo(fakeTodoRepository)
        updateTodo = UpdateTodo(fakeTodoRepository)
        todoListViewModel = TodoListViewModel(getAllTodos, deleteTodo, updateTodo)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get todos from empty list, todo list is empty`() = runTest {
        fakeTodoRepository.shouldHaveFilledList(false)

        val uiState: StateFlow<TodoUiState> = getAllTodos()
            .map(::Success)
            .catch { TodoUiState.Error(it) }
            .onEach { println("State emitted: $it") }
            .stateIn(this, SharingStarted.Eagerly, TodoUiState.Loading)

        assertEquals(TodoUiState.Loading, uiState.value)

        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            assertEquals(Success(emptyList()), uiState.value)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get todos from list, return success with list`() = runTest {
        fakeTodoRepository.shouldHaveFilledList(true)
        val list = mutableListOf(
            TodoItem("Todo 1", "Todo 1", false),
            TodoItem("Todo 2", "Todo 2", false),
            TodoItem("Todo 3", "Todo 3", false),
            TodoItem("Todo 4", "Todo 4", false)
        )
        val uiState: StateFlow<TodoUiState> = getAllTodos()
            .map(::Success)
            .catch { TodoUiState.Error(it) }
            .onEach { println("State emitted: $it") }
            .stateIn(this, SharingStarted.Eagerly, TodoUiState.Loading)

        assertEquals(TodoUiState.Loading, uiState.value)

        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            assertEquals(Success(list), uiState.value)
        }
    }

    @Test
    fun `delete todo from list, the todo is deleted`() = runTest {
        fakeTodoRepository.shouldHaveFilledList(true)
        val todo = TodoItem("Todo 2", "Todo 2", false)


        todoListViewModel.deleteTodo(todo)

        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        val uiState: StateFlow<TodoUiState> = getAllTodos()
            .map(::Success)
            .catch { TodoUiState.Error(it) }
            .onEach { println("State emitted: $it") }
            .stateIn(this, SharingStarted.Eagerly, TodoUiState.Loading)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            uiState.collect {
                if (it is Success) {
                    assertFalse(it.todos.contains(todo))
                }
            }
        }
    }
}