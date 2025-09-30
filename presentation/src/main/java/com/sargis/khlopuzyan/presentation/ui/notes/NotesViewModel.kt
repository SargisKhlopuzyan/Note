package com.sargis.khlopuzyan.presentation.ui.notes

import androidx.lifecycle.viewModelScope
import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.usecase.NoteUseCases
import com.sargis.khlopuzyan.domain.util.NoteOrder
import com.sargis.khlopuzyan.presentation.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NotesViewModel(
    private val noteUseCases: NoteUseCases,
) : BaseViewModel<NotesUiState, NotesUiEvent>() {

    override val _uiState: MutableStateFlow<NotesUiState> = MutableStateFlow(NotesUiState())

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(uiState.value.noteOrder)
    }

    override fun onEvent(event: NotesUiEvent) {
        when (event) {
            is NotesUiEvent.Order -> changeOrder(event.noteOrder)
            is NotesUiEvent.DeleteNote -> deleteNote(event.note)
            NotesUiEvent.RestoreNote -> restoreNote()
            NotesUiEvent.ToggleOrderSection -> toggleOrderSection()
        }
    }

    private fun changeOrder(noteOrder: NoteOrder) {
        if (uiState.value.noteOrder::class == noteOrder::class
            && uiState.value.noteOrder.orderType == noteOrder.orderType
        ) {
            return
        }
        getNotes(noteOrder)
    }

    private fun toggleOrderSection() {
        updateUiState {
            it.copy(
                orderSectionVisible = !it.orderSectionVisible
            )
        }
    }

    private fun deleteNote(note: Note) {
        viewModelScope.launch {
//            noteUseCases.deleteNote.deleteNote(note)
            noteUseCases.deleteNote(note)
            recentlyDeletedNote = note
        }
    }

    private fun restoreNote() {
        viewModelScope.launch {
//            noteUseCases.insertNoteUseCase.insertNote(recentlyDeletedNote ?: return@launch)
            noteUseCases.insertNote(recentlyDeletedNote ?: return@launch)
            recentlyDeletedNote = null
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
//        getNotesJob = noteUseCases.getNotesUseCase.getNotes(noteOrder).onEach { notes ->
        getNotesJob = noteUseCases.getNotes(noteOrder).onEach { notes ->
            updateUiState {
                it.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
        }.launchIn(viewModelScope)
    }

//    private fun getNotes(noteOrder: NoteOrder) {
//        getNotesJob?.cancel()
//        getNotesJob = viewModelScope.launch {
//            noteUseCases.getNotesUseCase.getNotes(noteOrder).onEach { notes ->
//                updateUiState {
//                    it.copy(
//                        notes = notes,
//                        noteOrder = noteOrder
//                    )
//                }
//            }
//        }
//    }
}