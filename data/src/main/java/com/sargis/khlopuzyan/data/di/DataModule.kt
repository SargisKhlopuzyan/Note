package com.sargis.khlopuzyan.data.di

import org.koin.dsl.module

private val databaseModule = module {
//    single<ShoppingListDatabase> {
//        ShoppingListDatabase.getInstance(get())
//    }
//    single<ShoppingListDao> {
//        get<ShoppingListDatabase>().shoppingListDao()
//    }
//    single<ShoppingListDataStore> {
//        ShoppingListDataStoreImpl(get())
//    }
}

private val repositoryModule = module {
//    single<PixabayApi> { PixabayApiRetrofitBuilder.build() }
//    single<PixabayRepository> { PixabayRepositoryImpl(get()) }
//    single<ShoppingListRepository> { ShoppingListRepositoryImpl(get()) }
//    single<MediaStoreUtil> { MediaStoreUtil(get()) }
//    single<MediaStoreRepository> { MediaStoreRepositoryImpl(get()) }
}

private val utilitiesModule = module {
}

val dataModule = listOf(databaseModule, repositoryModule, utilitiesModule)