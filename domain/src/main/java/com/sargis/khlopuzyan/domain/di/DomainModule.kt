package com.sargis.khlopuzyan.domain.di

import org.koin.dsl.module

private val useCaseModule = module {
//    single<SearchImagesUseCase> { SearchImagesUseCaseImpl(get()) }
//    single<GetShoppingListItemsUseCase> { GetShoppingListItemsUseCaseImpl(get()) }
//    single<GetShoppingListItemUseCase> { GetShoppingListItemUseCaseImpl(get()) }
//    single<SaveShoppingListItemUseCase> { SaveShoppingListItemUseCaseImpl(get()) }
//    single<SaveImageUseCase> { SaveImageUseCaseImpl(get()) }
//    single<ShareImageUseCase> { ShareImageUseCaseImpl(get()) }
}

val domainModule = listOf(useCaseModule/*, utilsModule*/)