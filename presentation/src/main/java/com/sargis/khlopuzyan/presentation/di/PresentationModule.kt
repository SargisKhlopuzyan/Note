package com.sargis.khlopuzyan.presentation.di

import org.koin.dsl.module

private val viewModelModule = module {
//    viewModel {
//        ShoppingListViewModel(get())
//    }
}

val presentationModule = listOf(viewModelModule)