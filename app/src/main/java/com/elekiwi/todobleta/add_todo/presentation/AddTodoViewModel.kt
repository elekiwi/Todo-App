package com.elekiwi.todobleta.add_todo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elekiwi.todobleta.add_todo.domain.use_case.UpsertTodo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(
    private val upsertTodo: UpsertTodo
): ViewModel(){

    private val _addTodoState = MutableStateFlow(AddTodoState())
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
                    it.copy(title = action.newDescription)
                }
            }
            AddTodoActions.SaveTodo -> {
                viewModelScope.launch {
                    val isSaved = upsertTodo(
                        title = _addTodoState.value.title,
                        description = _addTodoState.value.description,
                        isDone = _addTodoState.value.isDone
                    )

                    _todoSavedChannel.send(isSaved)
                }
            }

        }
    }

    suspend fun upsertTodo(
        title: String,
        description: String,
        isDone: Boolean
    ): Boolean {
        return upsertTodo.invoke(
            title = title,
            description = description,
            isDone = isDone
        )
    }
}