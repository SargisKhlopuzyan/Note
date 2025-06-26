package com.sargis.khlopuzyan.presentation.di

import org.koin.dsl.module

private val viewModelModule = module {
//    viewModel {
//        ShoppingListViewModel(get())
//    }
//    viewModel {
//        ShoppingListDetailViewModel(get(), get(), get())
//    }
//    viewModel {
//        ShoppingListAddViewModel(get())
//    }
//    viewModel {
//        ImageSearchViewModel(get())
//    }
}

val presentationModule = listOf(viewModelModule)