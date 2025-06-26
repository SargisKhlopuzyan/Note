package com.sargis.khlopuzyan.domain.repository

import com.sargis.khlopuzyan.domain.entity.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Int): Note?
    suspend fun insertNote(node: Note)
    suspend fun deleteNote(node: Note)
}