package com.sargis.khlopuzyan.domain.usecase

import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.repository.NoteRepository

interface InsertNoteUseCase {
    suspend fun insertNote(note: Note): Int
}

class InsertNoteUseCaseImpl(
    val noteRepository: NoteRepository,
) : InsertNoteUseCase {
    override suspend fun insertNote(note: Note): Int {
        return noteRepository.insertNote(note)
    }
}