package com.sargis.khlopuzyan.presentation.ui.navigation

sealed class NoteScreen(val route: String) {
    object NotesScreen: NoteScreen("notes_screen")
    object NoteDetailScreen: NoteScreen("note_detail_screen")
}