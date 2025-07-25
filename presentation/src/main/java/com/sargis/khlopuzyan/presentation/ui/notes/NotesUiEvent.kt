package com.sargis.khlopuzyan.presentation.ui.notes

import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.util.NoteOrder
import com.sargis.khlopuzyan.presentation.base.UIEvent

sealed interface NotesUiEvent : UIEvent {
    data class Order(val noteOrder: NoteOrder) : NotesUiEvent
    data class DeleteNote(val note: Note) : NotesUiEvent
    object RestoreNote : NotesUiEvent
    object ToggleOrderSection : NotesUiEvent
}