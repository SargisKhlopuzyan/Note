package com.sargis.khlopuzyan.domain.usecase

import com.sargis.khlopuzyan.domain.entity.InvalidNoteException
import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.repository.NoteRepository

interface InsertNoteUseCase {
    @Throws(InvalidNoteException::class)
    suspend fun insertNote(note: Note)
}

class InsertNoteUseCaseImpl(
    val noteRepository: NoteRepository,
) : InsertNoteUseCase {

    @Throws(InvalidNoteException::class)
    override suspend fun insertNote(note: Note) {
        if (note.title.isEmpty()) {
            throw InvalidNoteException("The title of the note can't be empty.")
        }
        if (note.content.isEmpty()) {
            throw InvalidNoteException("The content of the note can't be empty.")
        }
        noteRepository.insertNote(note)
    }
}