package com.elekiwi.todobleta.add_todo.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elekiwi.todobleta.R
import com.elekiwi.todobleta.core.presentation.util.TestTags
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddTodoScreen(
    onSave: () -> Unit,
    id: Int,
    addTodoViewModel: AddTodoViewModel = hiltViewModel()
) {
    val addTodoState by addTodoViewModel.addTodoState.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(true) {

        addTodoViewModel.onAction(AddTodoActions.LoadTodo(id))

        addTodoViewModel.todoSavedFlow.collectLatest { saved ->
            if (saved) {
                onSave()
            } else {
                Toast.makeText(
                    context,
                    "Invalid Info",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag(TestTags.TITLE_TEXT_FIELD),
            value = addTodoState.title,
            onValueChange = {
                addTodoViewModel.onAction(
                    AddTodoActions.UpdateTitle(it)
                )
            },
            label = {
                Text(text = stringResource(R.string.title))
            },
            maxLines = 4
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag(TestTags.DESCRIPTION_TEXT_FIELD),
            value = addTodoState.description,
            onValueChange = {
                addTodoViewModel.onAction(
                    AddTodoActions.UpdateDescription(it)
                )
            },
            label = {
                Text(text = stringResource(R.string.description))
            },
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag(TestTags.SAVE_BUTTON),
            onClick = {
                addTodoViewModel.onAction(
                    AddTodoActions.SaveTodo
                )
            }
        ) {
            Text(
                text = stringResource(R.string.save),
                fontSize = 17.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}