package com.sargis.khlopuzyan.presentation.ui.note

import androidx.lifecycle.viewModelScope
import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.usecase.DeleteNoteUseCase
import com.sargis.khlopuzyan.domain.usecase.GetNoteByIdUseCase
import com.sargis.khlopuzyan.domain.usecase.GetNotesUseCase
import com.sargis.khlopuzyan.domain.usecase.InsertNoteUseCase
import com.sargis.khlopuzyan.domain.util.NoteOrder
import com.sargis.khlopuzyan.presentation.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NotesViewModel(
    private val getNotesUseCase: GetNotesUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
) : BaseViewModel<NotesUiState, NotesUiEvent>() {

    override val _uiState: MutableStateFlow<NotesUiState>
        get() = MutableStateFlow(NotesUiState())

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
            NotesUiEvent.AddNote -> addNote()
            is NotesUiEvent.UpdateNote -> updateNote()
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
            deleteNoteUseCase.deleteNote(note)
            recentlyDeletedNote = note
        }
    }

    private fun restoreNote() {
        viewModelScope.launch {
            insertNoteUseCase.insertNote(recentlyDeletedNote ?: return@launch)
            recentlyDeletedNote = null
        }
    }

    private fun addNote() {

    }

    private fun updateNote() {

    }

    private fun getNotes(noteOrder: NoteOrder) {
//        viewModelScope.launch {
//            getNotesUseCase.getNotes(noteOrder).collect {
//
//            }
//        }

        getNotesJob?.cancel()
        getNotesJob = getNotesUseCase.getNotes(noteOrder).onEach { notes ->
            updateUiState {
                it.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
        }.launchIn(viewModelScope)
    }
}