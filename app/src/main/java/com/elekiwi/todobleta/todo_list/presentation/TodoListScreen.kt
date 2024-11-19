package com.elekiwi.todobleta.todo_list.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.elekiwi.todobleta.R
import com.elekiwi.todobleta.core.domain.model.TodoItem
import com.elekiwi.todobleta.core.presentation.util.TestTags

@Composable

fun TodoListScreen(
    onNavigateToAddTodo: () -> Unit,
    onEditClick: (Int) -> Unit,
    viewModel: TodoListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<TodoUiState>(
        initialValue = TodoUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }

    when (uiState) {
        is TodoUiState.Error -> Toast.makeText(
            context,
            "Some error has ocurred: ${(uiState as TodoUiState.Error).throwable.message}",
            Toast.LENGTH_SHORT
        ).show()

        TodoUiState.Loading -> CircularProgressIndicator()
        is TodoUiState.Success -> {
            ScaffoldView(
                onNavigateToAddNote = onNavigateToAddTodo,
                onEditClick = { onEditClick(it) },
                viewModel = viewModel,
                todos = (uiState as TodoUiState.Success).todos
            )
        }
    }
}

@Composable
private fun ScaffoldView(
    onNavigateToAddNote: () -> Unit,
    onEditClick: (Int) -> Unit,
    viewModel: TodoListViewModel,
    todos: List<TodoItem>
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.tasks, todos.size),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 19.sp,
                )

            }
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .testTag(TestTags.ADD_TODO_FAB),
                onClick = onNavigateToAddNote
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_a_task)
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {

            items(
                count = todos.size,
                key = { it }
            ) { index ->
                TodoListItem(
                    onDelete = {
                        viewModel.deleteTodo(todos[index])
                    },
                    onEditClick = {
                        onEditClick(it)
                    },
                    onCheckedChange = {
                        viewModel.onCheckboxSelected(todos[index])
                    },
                    todos[index]
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun TodoListItem(
    onDelete: () -> Unit,
    onEditClick: (Int) -> Unit,
    onCheckedChange: () -> Unit,
    todoItem: TodoItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(
                BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                RoundedCornerShape(20.dp)
            )
            .padding(8.dp)
            .clickable { onEditClick(todoItem.id) }
    ) {

        Spacer(modifier = Modifier.width(16.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically
        ) {


            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(vertical = 8.dp)
            ) {

                Text(
                    text = todoItem.title,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 19.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = todoItem.description,
                    color = Color.Black,
                    fontSize = 15.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

            }

            Checkbox(
                checked = todoItem.isDone,
                onCheckedChange = { onCheckedChange() })
        }

        Icon(
            modifier = Modifier
                .clickable { onDelete() },
            imageVector = Icons.Default.Clear,
            contentDescription = TestTags.DELETE_TODO + todoItem.title,
            tint = Color.Black,
        )
    }
}

