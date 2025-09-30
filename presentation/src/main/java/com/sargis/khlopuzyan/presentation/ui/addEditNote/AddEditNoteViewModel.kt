package com.sargis.khlopuzyan.presentation.ui.addEditNote

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sargis.khlopuzyan.domain.entity.InvalidNoteException
import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.usecase.NoteUseCases
import com.sargis.khlopuzyan.presentation.util.NoteUtil.noteColors
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AddEditNoteViewModel(
    private val noteUseCase: NoteUseCases,
) : ViewModel() {

    private val _noteTitleState = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter title..."
        )
    )
    val noteTitleState: State<NoteTextFieldState> = _noteTitleState

    private val _noteContentState = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter some content"
        )
    )
    val noteContentState: State<NoteTextFieldState> = _noteContentState

    private val _noteColorState = mutableIntStateOf(noteColors().random())
    val noteColorState: State<Int> = _noteColorState


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


    fun onEvent(event: AddEditNoteUiEvent) {
        when (event) {
            is AddEditNoteUiEvent.SetNoteId -> {
                loadNote(event.id)
            }

            is AddEditNoteUiEvent.EnteredTitle -> {
                _noteTitleState.value = noteTitleState.value.copy(
                    text = event.value,
                )
            }

            is AddEditNoteUiEvent.ChangeTitleFocus -> {
                _noteTitleState.value = noteTitleState.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitleState.value.text.isEmpty()
                )
            }

            is AddEditNoteUiEvent.EnteredContent -> {
                _noteContentState.value = noteContentState.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteUiEvent.ChangeContentFocus -> {
                _noteContentState.value = noteContentState.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteContentState.value.text.isEmpty()
                )
            }

            is AddEditNoteUiEvent.ChangeColor -> {
                _noteColorState.intValue = event.color
            }

            AddEditNoteUiEvent.SaveNote -> {
                saveNote()
            }
        }
    }

    private fun loadNote(noteId: Int?) {
        if (noteId != null) {
            viewModelScope.launch {
//                noteUseCase.getNoteByIdUseCase.getNoteById(noteId)?.also { note ->
                noteUseCase.getNoteById(noteId)?.also { note ->
                    currentNoteId = noteId
                    _noteTitleState.value = noteTitleState.value.copy(
                        text = note.title,
                        isHintVisible = false
                    )
                    _noteContentState.value = noteContentState.value.copy(
                        text = note.content,
                        isHintVisible = false
                    )
                    _noteColorState.intValue = note.color
                }
            }
        }
    }

    private fun saveNote() {
        viewModelScope.launch {
            try {
//                noteUseCase.insertNoteUseCase.insertNote(
                noteUseCase.insertNote(
                    Note(
                        id = currentNoteId,
                        title = _noteTitleState.value.text,
                        content = _noteContentState.value.text,
                        timeStamp = System.currentTimeMillis(),
                        color = _noteColorState.intValue
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