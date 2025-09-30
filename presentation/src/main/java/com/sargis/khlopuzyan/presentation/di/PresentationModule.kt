package com.sargis.khlopuzyan.presentation.di

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

val presentationModule = listOf(viewModelModule)