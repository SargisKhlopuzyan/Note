package com.sargis.khlopuzyan.presentation.ui.addEditNote

import androidx.compose.ui.focus.FocusState
import com.sargis.khlopuzyan.presentation.base.UIEvent

sealed interface AddEditNoteUiEvent : UIEvent {
    data class SetNoteId(val id: Int) : AddEditNoteUiEvent
    data class EnteredTitle(val value: String) : AddEditNoteUiEvent
    data class ChangeTitleFocus(val focusState: FocusState) : AddEditNoteUiEvent
    data class EnteredContent(val value: String) : AddEditNoteUiEvent
    data class ChangeContentFocus(val focusState: FocusState) : AddEditNoteUiEvent
    data class ChangeColor(val color: Int) : AddEditNoteUiEvent
    object SaveNote : AddEditNoteUiEvent
}

sealed interface UICallbackEvent {
    object SaveNote : UICallbackEvent
    data class ShowSnakebar(val message: String) : UICallbackEvent
}