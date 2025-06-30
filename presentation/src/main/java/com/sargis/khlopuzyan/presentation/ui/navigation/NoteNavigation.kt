package com.sargis.khlopuzyan.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sargis.khlopuzyan.presentation.ui.addEditNote.AddEditNoteScreen
import com.sargis.khlopuzyan.presentation.ui.note.NotesScreen

@Composable
fun NoteNavigation() {
    val navController = rememberNavController()
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
            val noteColor = backStackEntry.arguments?.getInt("noteColor")?.let { color ->
                if (color == -1) null else color
            }
            AddEditNoteScreen(navController, noteId, noteColor)
        }
    }
}