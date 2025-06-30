package com.sargis.khlopuzyan.domain.usecase

import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.repository.NoteRepository

interface GetNoteByIdUseCase {
    suspend fun getNoteById(id: Int): Note?
}

class GetNoteByIdUseCaseImpl(
    val noteRepository: NoteRepository,
) : GetNoteByIdUseCase {
    override suspend fun getNoteById(id: Int): Note? {
        return noteRepository.getNoteById(id)
    }
}

//class GetNoteByIdUseCase(
//    val noteRepository: NoteRepository,
//) {
//    suspend operator fun invoke(id: Int): Note? {
//        return noteRepository.getNoteById(id)
//    }
//}