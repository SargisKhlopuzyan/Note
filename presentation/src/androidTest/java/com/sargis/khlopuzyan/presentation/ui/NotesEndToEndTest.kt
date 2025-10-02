package com.sargis.khlopuzyan.presentation.ui

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sargis.khlopuzyan.presentation.ui.addEditNote.AddEditNoteScreen
import com.sargis.khlopuzyan.presentation.ui.navigation.NoteScreen
import com.sargis.khlopuzyan.presentation.ui.notes.NotesScreen
import com.sargis.khlopuzyan.presentation.ui.theme.NoteTheme
import com.sargis.khlopuzyan.presentation.util.TestTags
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest

class NotesEndToEndTest : KoinTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeRule.activity.setContent {
            val navController = rememberNavController()
            NoteTheme {
                NavHost(
                    navController = navController,
                    startDestination = NoteScreen.NotesScreen.route
                ) {
                    composable(route = NoteScreen.NotesScreen.route) {
                        NotesScreen(navController)
                    }
                    composable(
                        route = NoteScreen.AddEditNoteScreen.route + "?noteId={noteId}&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument("noteId") {
                                type = NavType.IntType
                                defaultValue = -1
                                nullable = false
                            },
                            navArgument("noteColor") {
                                type = NavType.IntType
                                defaultValue = -1
                                nullable = false
                            }
                        )
                    ) { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getInt("noteId")?.let { id ->
                            if (id == -1) null else id
                        }
                        val noteColor =
                            backStackEntry.arguments?.getInt("noteColor")?.let { color ->
                                if (color == -1) null else color
                            }
                        AddEditNoteScreen(navController, noteId, noteColor)
                    }
                }
            }
        }
    }

    @Test
    fun saveNewNote_editAfterwords() {
        // Click on FAB to get to add note screen
        // 1st screen
        composeRule.onNodeWithContentDescription("Add").performClick()
        // 2nd screen
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("test-title")
        composeRule
            .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
            .performTextInput("test-content")
        composeRule.onNodeWithContentDescription("Save").performClick()

        // 1st screen
        composeRule.onNodeWithText("test-title").assertIsDisplayed()
        composeRule.onNodeWithText("test-title").performClick()

        // 2nd screen
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .assertTextEquals("test-title")
        composeRule
            .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
            .assertTextEquals("test-content")

        // 2nd screen
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
//            .onChildren()
//            .filterToOne(hasSetTextAction())
//            .performClick()
            .performTextInput("-2")

/*        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .assertTextEquals("test-title-abc")*/

//        composeRule
//            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
//            .performTextReplacement("123456")

        composeRule
            .onNodeWithContentDescription("Save")
            .performClick()

        composeRule
            .onNodeWithText("test-title2")
            .assertIsDisplayed()
    }
}