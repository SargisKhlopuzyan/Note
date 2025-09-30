package com.sargis.khlopuzyan.data.repository

import com.sargis.khlopuzyan.data.local.entity.toNote
import com.sargis.khlopuzyan.data.local.entity.toNoteEntity
import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeNoteRepository : NoteRepository {

    private val notes = mutableListOf<Note>()

    private val _flow: MutableStateFlow<List<Note>> = MutableStateFlow<List<Note>>(emptyList())
    private val flow: StateFlow<List<Note>> = _flow.asStateFlow()

    override fun getNotes(): Flow<List<Note>> {
        return flow
    }

    override suspend fun getNoteById(id: Int): Note? {
        return notes.find {
            it.id == id
        }?.toNoteEntity()?.toNote()
    }

    override suspend fun insertNote(note: Note) {
        val index = notes.indexOfFirst { it.id == note.id }
        if (index >= 0) {
            notes[index] = note.toNoteEntity().toNote()
        } else {
            notes.add(note.toNoteEntity().toNote().copy(id = notes.size))
        }
        _flow.tryEmit(notes.toTypedArray().toList())
    }

    override suspend fun deleteNote(note: Note) {
        notes.removeIf { it.id == note.id }
        _flow.tryEmit(notes.toTypedArray().toList())
    }
}