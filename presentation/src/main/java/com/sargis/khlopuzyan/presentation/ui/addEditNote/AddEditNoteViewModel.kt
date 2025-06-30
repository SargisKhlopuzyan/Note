package com.sargis.khlopuzyan.presentation.ui.addEditNote

import androidx.lifecycle.viewModelScope
import com.sargis.khlopuzyan.domain.entity.InvalidNoteException
import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.usecase.NoteUseCases
import com.sargis.khlopuzyan.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AddEditNoteViewModel(
    private val noteUseCase: NoteUseCases,
//    savedStateHandle: SavedStateHandle,
) : BaseViewModel<AddEditNoteUiState, AddEditNoteUiEvent>() {

    override val _uiState: MutableStateFlow<AddEditNoteUiState> =
        MutableStateFlow(AddEditNoteUiState())

    private val _showToastEventFlow: MutableSharedFlow<UICallbackEvent> =
        MutableSharedFlow<UICallbackEvent>()
    val showToastEventFlow: SharedFlow<UICallbackEvent> = _showToastEventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

//    init {
//        savedStateHandle.get<Int>("noteId")?.let { noteId ->
//            if (noteId != -1) {
//                viewModelScope.launch {
//                    noteUseCase.getNoteByIdUseCase.getNoteById(noteId)?.also { note ->
//                        currentNoteId = noteId
//                        uiState.value.noteTitleState.value =
//                            uiState.value.noteTitleState.value.copy(
//                                text = note.title,
//                                isHintVisible = false
//                            )
//                        uiState.value.noteContentState.value =
//                            uiState.value.noteContentState.value.copy(
//                                text = note.content,
//                                isHintVisible = false
//                            )
//                        uiState.value.noteColorState.intValue = note.color
//                    }
//                }
//            }
//        }
//    }


    override fun onEvent(event: AddEditNoteUiEvent) {
        when (event) {
            is AddEditNoteUiEvent.SetNoteId -> {
                loadNote(event.id)
            }

            is AddEditNoteUiEvent.EnteredTitle -> {
                uiState.value.noteTitleState.value = uiState.value.noteTitleState.value.copy(
                    text = event.value,
                )
            }

            is AddEditNoteUiEvent.ChangeTitleFocus -> {
                uiState.value.noteTitleState.value = uiState.value.noteTitleState.value.copy(
                    isHintVisible = !event.focusState.isFocused && uiState.value.noteTitleState.value.text.isEmpty()
                )
            }

            is AddEditNoteUiEvent.EnteredContent -> {
                uiState.value.noteContentState.value = uiState.value.noteContentState.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteUiEvent.ChangeContentFocus -> {
                uiState.value.noteContentState.value = uiState.value.noteContentState.value.copy(
                    isHintVisible = !event.focusState.isFocused && uiState.value.noteContentState.value.text.isEmpty()
                )
            }

            is AddEditNoteUiEvent.ChangeColor -> {
                uiState.value.noteColorState.intValue = event.color
            }

            AddEditNoteUiEvent.SaveNote -> {
                saveNote()
            }
        }
    }

    private fun loadNote(noteId: Int?) {
        if (noteId != null) {
            viewModelScope.launch {
                noteUseCase.getNoteByIdUseCase.getNoteById(noteId)?.also { note ->
                    currentNoteId = noteId
                    uiState.value.noteTitleState.value =
                        uiState.value.noteTitleState.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                    uiState.value.noteContentState.value =
                        uiState.value.noteContentState.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                    uiState.value.noteColorState.intValue = note.color
                }
            }
        }
    }

    private fun saveNote() {
        viewModelScope.launch {
            try {
                noteUseCase.insertNoteUseCase.insertNote(
                    Note(
                        id = currentNoteId,
                        title = uiState.value.noteTitleState.value.text,
                        content = uiState.value.noteContentState.value.text,
                        timeStamp = System.currentTimeMillis(),
                        color = uiState.value.noteColorState.intValue
                    )
                )
                _showToastEventFlow.emit(UICallbackEvent.SaveNote)
            } catch (e: InvalidNoteException) {
                _showToastEventFlow.emit(
                    UICallbackEvent.ShowSnakebar(e.message ?: "Couldn't save note")
                )
            }
        }
    }
}