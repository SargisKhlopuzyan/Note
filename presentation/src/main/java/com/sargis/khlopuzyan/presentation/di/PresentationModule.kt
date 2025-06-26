package com.sargis.khlopuzyan.presentation.di

import com.sargis.khlopuzyan.presentation.ui.note.NotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModelModule = module {
    viewModel {
        NotesViewModel(get(), get(), get(), get())
    }
}

val presentationModule = listOf(viewModelModule)