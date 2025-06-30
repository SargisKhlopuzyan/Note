package com.sargis.khlopuzyan.presentation.ui.navigation

sealed class NoteScreen(val route: String) {
    object NotesScreen : NoteScreen("notes_screen")
    object AddEditNoteScreen : NoteScreen("add_edit_note_screen")
}