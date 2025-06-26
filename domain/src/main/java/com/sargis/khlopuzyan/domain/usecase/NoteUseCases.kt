package com.sargis.khlopuzyan.domain.usecase

/**
 * This is in case we have loots of UseCases,
 * it is easier to have all in one place
 * */
data class NoteUseCases(
    val getNotesUseCases: GetNotesUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase,
    val insertNoteUseCase: InsertNoteUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
)