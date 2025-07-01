package com.sargis.khlopuzyan.presentation.ui.notes

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.sargis.khlopuzyan.presentation.ui.MainActivity
import com.sargis.khlopuzyan.presentation.ui.navigation.NoteScreen
import com.sargis.khlopuzyan.presentation.ui.theme.NoteTheme
import com.sargis.khlopuzyan.presentation.util.TestTags
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest

class NotesScreenTest : KoinTest {

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
                        NotesScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun clickToggleOrderSection_isVisible() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION, false).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION, false).assertExists()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION, false).assertIsDisplayed()
    }
}