package com.sargis.khlopuzyan.domain.di

import com.sargis.khlopuzyan.domain.usecase.DeleteNote
import com.sargis.khlopuzyan.domain.usecase.GetNoteById
import com.sargis.khlopuzyan.domain.usecase.GetNotes
import com.sargis.khlopuzyan.domain.usecase.InsertNote
import com.sargis.khlopuzyan.domain.usecase.NoteUseCases
import org.koin.dsl.module

private val useCaseModule = module {
//    single<GetNotesUseCase> { GetNotesUseCaseImpl(get()) }
//    single<GetNoteByIdUseCase> { GetNoteByIdUseCaseImpl(get()) }
//    single<InsertNoteUseCase> { InsertNoteUseCaseImpl(get()) }
//    single<DeleteNoteUseCase> { DeleteNoteUseCaseImpl(get()) }

    single<GetNotes> { GetNotes(get()) }
    single<GetNoteById> { GetNoteById(get()) }
    single<InsertNote> { InsertNote(get()) }
    single<DeleteNote> { DeleteNote(get()) }

    single<NoteUseCases> { NoteUseCases(get(), get(), get(), get()) }
}

private val utilityModule = module {
}

val domainModule = listOf(useCaseModule, utilityModule)