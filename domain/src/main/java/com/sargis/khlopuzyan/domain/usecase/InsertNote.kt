package com.sargis.khlopuzyan.domain.usecase

import com.sargis.khlopuzyan.domain.entity.InvalidNoteException
import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.repository.NoteRepository

//interface InsertNoteUseCase {
//    @Throws(InvalidNoteException::class)
//    suspend fun insertNote(note: Note)
//}
//
//class InsertNoteUseCaseImpl(
//    val noteRepository: NoteRepository,
//) : InsertNoteUseCase {
//
//    @Throws(InvalidNoteException::class)
//    override suspend fun insertNote(note: Note) {
//        if (note.title.isBlank()) {
//            throw InvalidNoteException("The title of the note can't be empty.")
//        }
//        if (note.content.isBlank()) {
//            throw InvalidNoteException("The content of the note can't be empty.")
//        }
//        noteRepository.insertNote(note)
//    }
//}

class InsertNote(
    private val noteRepository: NoteRepository,
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("The title of the note can't be empty.")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty.")
        }
        noteRepository.insertNote(note)
    }
}