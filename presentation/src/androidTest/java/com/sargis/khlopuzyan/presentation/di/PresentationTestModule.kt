package com.sargis.khlopuzyan.presentation.di

import com.sargis.khlopuzyan.domain.repository.NoteRepository
import com.sargis.khlopuzyan.presentation.repository.NoteFakeRepository
import com.sargis.khlopuzyan.presentation.ui.addEditNote.AddEditNoteViewModel
import com.sargis.khlopuzyan.presentation.ui.notes.NotesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private val viewModelModule = module {
    viewModel {
        NotesViewModel(get())
    }
    viewModel {
        AddEditNoteViewModel(get())
    }
}

// TODO - without this test will crash as NoteRepository will not be injected
private val repositoryTestModule = module {
    single<NoteRepository> { NoteFakeRepository() }
}

val presentationTestModule = listOf(viewModelModule, repositoryTestModule)