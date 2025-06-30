package com.sargis.khlopuzyan.presentation.ui.addEditNote

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.presentation.base.UIState

data class AddEditNoteUiState(
    val noteTitleState: MutableState<NoteTextFieldState> = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter title..."
        )
    ),
    val noteContentState: MutableState<NoteTextFieldState> = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter some content..."
        )
    ),
    val noteColorState: MutableIntState = mutableIntStateOf(Note.RedOrange),
) : UIState

data class NoteTextFieldState(
    var text: String = "",
    var hint: String = "",
    var isHintVisible: Boolean = true,
)