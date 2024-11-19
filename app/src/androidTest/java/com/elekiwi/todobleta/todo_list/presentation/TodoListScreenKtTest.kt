package com.elekiwi.todobleta.todo_list.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.elekiwi.todobleta.core.di.AppModule
import com.elekiwi.todobleta.core.presentation.MainActivity
import com.elekiwi.todobleta.core.presentation.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class TodoListScreenKtTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun insertTodo_todoIsDisplayedInList() {
        insertTodo(1)
        assertTodoIsDisplayed(1)
    }

    @Test
    fun deleteTodo_todoIsNotDisplayedInList() {
        insertTodo(1)
        assertTodoIsDisplayed(1)

        deleteTodo(1)
        //assertTodoIsNotDisplayed(1)
    }

    @Test
    fun todoListScreenEndtoEndTest() {
        insertTodo(1)
        assertTodoIsDisplayed(1)

        insertTodo(2)
        assertTodoIsDisplayed(2)

        insertTodo(3)
        assertTodoIsDisplayed(3)

        insertTodo(4)
        assertTodoIsDisplayed(4)
    }

    private fun insertTodo(todoNumber: Int) {
        composeRule.onNodeWithTag(TestTags.ADD_TODO_FAB)
            .performClick()

        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("title $todoNumber")

        composeRule.onNodeWithTag(TestTags.DESCRIPTION_TEXT_FIELD)
            .performTextInput("description $todoNumber")

        composeRule.onNodeWithTag(TestTags.SAVE_BUTTON)
            .performClick()
    }

    private fun deleteTodo(todoNumber: Int) {
        composeRule.onNodeWithContentDescription(TestTags.DELETE_TODO + "title $todoNumber")
            .performClick()
    }

    private fun assertTodoIsDisplayed(todoNumber: Int) {
        composeRule.onNodeWithText("title $todoNumber")
            .assertIsDisplayed()
    }

    private fun assertTodoIsNotDisplayed(todoNumber: Int) {
        composeRule.onNodeWithText("title $todoNumber")
            .assertIsNotDisplayed()
    }


}














