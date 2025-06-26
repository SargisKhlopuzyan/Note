package com.sargis.khlopuzyan.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
    }
}