package com.sargis.khlopuzyan.domain.usecase

import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.repository.NoteRepository

//interface DeleteNoteUseCase {
//    suspend fun deleteNote(note: Note)
//}
//
//class DeleteNoteUseCaseImpl(
//    val noteRepository: NoteRepository,
//) : DeleteNoteUseCase {
//    override suspend fun deleteNote(note: Note) {
//        return noteRepository.deleteNote(note)
//    }
//}

class DeleteNoteUseCase(
    private val noteRepository: NoteRepository,
) {
    suspend operator fun invoke(note: Note) {
        return noteRepository.deleteNote(note)
    }
}