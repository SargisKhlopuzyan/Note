package com.sargis.khlopuzyan.data.local.source

import com.sargis.khlopuzyan.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAndroidNoteDataSource : NoteDataSource {

    private val notes = mutableListOf<NoteEntity>()

    override fun getNotes(): Flow<List<NoteEntity>> {
        return flow {
            listOf(notes)
        }
    }

    override suspend fun getNoteById(id: Int): NoteEntity? {
        return notes.find { it.id == id }
    }

    override suspend fun insertNote(note: NoteEntity) {
        val index = notes.indexOfFirst { it.id == note.id }
        if (index >= 0) {
            notes[index] = note
        } else {
            notes.add(note)
        }
    }

    override suspend fun deleteNote(note: NoteEntity) {
        notes.removeIf { it.id == note.id }
    }
}