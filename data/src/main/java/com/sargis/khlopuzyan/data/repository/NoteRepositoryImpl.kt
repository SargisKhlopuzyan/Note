package com.sargis.khlopuzyan.data.repository

import com.sargis.khlopuzyan.data.local.entity.toNote
import com.sargis.khlopuzyan.data.local.entity.toNoteEntity
import com.sargis.khlopuzyan.data.local.entity.toNotes
import com.sargis.khlopuzyan.data.local.source.NoteDataStore
import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    val noteDataStore: NoteDataStore,
) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return noteDataStore.getNotes().map { noteEntity ->
            noteEntity.toNotes()
        }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return noteDataStore.getNoteById(id)?.toNote()
    }

    override suspend fun insertNote(node: Note): Int {
        return noteDataStore.insertNote(node.toNoteEntity())
    }

    override suspend fun deleteNote(node: Note): Int {
        return noteDataStore.deleteNote(node.toNoteEntity())
    }
}