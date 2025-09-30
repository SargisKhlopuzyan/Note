package com.sargis.khlopuzyan.presentation.v1.di

import com.sargis.khlopuzyan.domain.repository.NoteRepository
import com.sargis.khlopuzyan.presentation.v1.repository.FakeNoteRepository
import com.sargis.khlopuzyan.presentation.ui.addEditNote.AddEditNoteViewModel
import com.sargis.khlopuzyan.presentation.ui.notes.NotesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private val viewModelModule = module {
    viewModel {
        NotesViewModel(get())
    }
    viewModel {
        AddEditNoteViewModel(get()/*, get()*/)
    }
}

// TODO - without this test will crash as NoteRepository will not be injected
private val repositoryModule = module {
    single<NoteRepository> { FakeNoteRepository() }
}

val testPresentationModule = listOf(viewModelModule, repositoryModule)