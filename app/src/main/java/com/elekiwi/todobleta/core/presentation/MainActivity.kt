package com.elekiwi.todobleta.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.elekiwi.todobleta.add_todo.presentation.AddTodoScreen
import com.elekiwi.todobleta.core.presentation.ui.theme.TodoBletaTheme
import com.elekiwi.todobleta.todo_list.presentation.TodoListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoBletaTheme {
               Navigation()
            }
        }
    }
}

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.TodoList
    ) {

        composable<Screen.TodoList> {
            TodoListScreen(
                onNavigateToAddTodo = {
                    navController.navigate(Screen.AddTodo(todoId = -1))
                },
                onEditClick = { todoId ->
                    navController.navigate(Screen.AddTodo(todoId = todoId))
                }
            )
        }

        composable<Screen.AddTodo> { backStackEntry ->
            val todoAddScreen: Screen.AddTodo = backStackEntry.toRoute()
            AddTodoScreen(
                onSave = {
                    navController.popBackStack()
                },
                id = todoAddScreen.todoId
            )
        }
    }
}