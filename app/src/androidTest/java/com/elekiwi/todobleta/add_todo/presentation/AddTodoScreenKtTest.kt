package com.elekiwi.todobleta.add_todo.presentation

import androidx.compose.ui.test.junit4.createAndroidComposeRule
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
class AddTodoScreenKtTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun addTodoScreenEndToEndTest() {
        composeRule.onNodeWithTag(TestTags.ADD_TODO_FAB)
            .performClick()

        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("title")

        composeRule.onNodeWithTag(TestTags.DESCRIPTION_TEXT_FIELD)
            .performTextInput("description")

        composeRule.onNodeWithTag(TestTags.SAVE_BUTTON)
            .performClick()

        composeRule.onNodeWithText("title")
            .assertExists()
    }
}