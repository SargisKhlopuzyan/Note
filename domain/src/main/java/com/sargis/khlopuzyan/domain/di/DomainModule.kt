package com.sargis.khlopuzyan.domain.di

import com.sargis.khlopuzyan.domain.usecase.DeleteNoteUseCase
import com.sargis.khlopuzyan.domain.usecase.DeleteNoteUseCaseImpl
import com.sargis.khlopuzyan.domain.usecase.GetNoteByIdUseCase
import com.sargis.khlopuzyan.domain.usecase.GetNoteByIdUseCaseImpl
import com.sargis.khlopuzyan.domain.usecase.GetNotesUseCase
import com.sargis.khlopuzyan.domain.usecase.GetNotesUseCaseImpl
import com.sargis.khlopuzyan.domain.usecase.InsertNoteUseCase
import com.sargis.khlopuzyan.domain.usecase.InsertNoteUseCaseImpl
import org.koin.dsl.module

private val useCaseModule = module {
    single<GetNotesUseCase> { GetNotesUseCaseImpl(get()) }
    single<GetNoteByIdUseCase> { GetNoteByIdUseCaseImpl(get()) }
    single<InsertNoteUseCase> { InsertNoteUseCaseImpl(get()) }
    single<DeleteNoteUseCase> { DeleteNoteUseCaseImpl(get()) }

//    single<NoteUseCases> { NoteUseCases(GetNotes(), ...) }
}

private val utilityModule = module {
}

val domainModule = listOf(useCaseModule, utilityModule)