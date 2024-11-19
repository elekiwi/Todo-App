package com.elekiwi.todobleta.add_todo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elekiwi.todobleta.add_todo.domain.use_case.UpsertTodo
import com.elekiwi.todobleta.core.domain.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(
    private val upsertTodo: UpsertTodo,
    private val todoRepository: TodoRepository
): ViewModel(){

    private var _addTodoState = MutableStateFlow(AddTodoState())
    val addTodoState = _addTodoState.asStateFlow()

    private val _todoSavedChannel = Channel<Boolean>()
    val todoSavedFlow = _todoSavedChannel.receiveAsFlow()

    fun onAction(action: AddTodoActions){
        when(action){
            is AddTodoActions.UpdateTitle -> {
                _addTodoState.update {
                    it.copy(title = action.newTitle)
                }
            }
            is AddTodoActions.UpdateDescription -> {
                _addTodoState.update {
                    it.copy(description = action.newDescription)
                }
            }
            AddTodoActions.SaveTodo -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val isSaved = upsertTodo(
                        id = _addTodoState.value.todoId,
                        title = _addTodoState.value.title,
                        description = _addTodoState.value.description,
                        isDone = _addTodoState.value.isDone
                    )

                    _todoSavedChannel.send(isSaved)
                }
            }

            is AddTodoActions.LoadTodo -> {
                loadTodo(action.id)
            }
        }
    }

    private fun loadTodo(id: Int) {

        if (id == -1) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val todo = todoRepository.getTodoById(id)

            todo.let {
                _addTodoState.update {
                    it.copy(title = todo!!.title, description = todo.description, isDone = todo.isDone)
                }
            }


        }
    }

    suspend fun upsertTodo(
        id: Int,
        title: String,
        description: String,
        isDone: Boolean
    ): Boolean {

        return upsertTodo.invoke(
            id = id,
            title = title,
            description = description,
            isDone = isDone
        )
    }
}