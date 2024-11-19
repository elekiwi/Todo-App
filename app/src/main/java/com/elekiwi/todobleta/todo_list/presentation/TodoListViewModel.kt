package com.elekiwi.todobleta.todo_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elekiwi.todobleta.core.domain.model.TodoItem
import com.elekiwi.todobleta.todo_list.domain.use_case.DeleteTodo
import com.elekiwi.todobleta.todo_list.domain.use_case.GetAllTodos
import com.elekiwi.todobleta.todo_list.domain.use_case.UpdateTodo
import com.elekiwi.todobleta.todo_list.presentation.TodoUiState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val getAllTodos: GetAllTodos,
    private val deleteTodo: DeleteTodo,
    private val updateTodo: UpdateTodo
): ViewModel() {

    val uiState: StateFlow<TodoUiState> = getAllTodos().map(::Success)
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    fun deleteTodo(todoItem: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTodo.invoke(todoItem)

        }
    }

    fun onCheckboxSelected(todoItem: TodoItem) {
       viewModelScope.launch(Dispatchers.IO) {
            updateTodo.invoke(todoItem.copy(isDone = !todoItem.isDone))
        }
    }
}