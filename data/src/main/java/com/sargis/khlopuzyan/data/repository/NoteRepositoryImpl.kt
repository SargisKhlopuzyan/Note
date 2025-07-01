package com.sargis.khlopuzyan.data.repository

import com.sargis.khlopuzyan.data.local.entity.toNote
import com.sargis.khlopuzyan.data.local.entity.toNoteEntity
import com.sargis.khlopuzyan.data.local.entity.toNotes
import com.sargis.khlopuzyan.data.local.source.NoteDataSource
import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    val noteDataStore: NoteDataSource,
) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return noteDataStore.getNotes().map { noteEntity ->
            noteEntity.toNotes()
        }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return noteDataStore.getNoteById(id)?.toNote()
    }

    override suspend fun insertNote(note: Note) {
        noteDataStore.insertNote(note.toNoteEntity())
    }

    override suspend fun deleteNote(note: Note) {
        noteDataStore.deleteNote(note.toNoteEntity())
    }
}