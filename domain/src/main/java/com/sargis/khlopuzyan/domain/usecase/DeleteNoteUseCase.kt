package com.sargis.khlopuzyan.domain.usecase

import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.repository.NoteRepository

interface DeleteNoteUseCase {
    suspend fun deleteNote(note: Note): Int
}

class DeleteNoteUseCaseImpl(
    val noteRepository: NoteRepository,
) : DeleteNoteUseCase {
    override suspend fun deleteNote(note: Note): Int {
        return noteRepository.deleteNote(note)
    }
}