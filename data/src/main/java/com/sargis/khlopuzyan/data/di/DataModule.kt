package com.sargis.khlopuzyan.data.di

import com.sargis.khlopuzyan.data.local.dao.NoteDao
import com.sargis.khlopuzyan.data.local.db.NoteDatabase
import com.sargis.khlopuzyan.data.local.source.NoteDataSource
import com.sargis.khlopuzyan.data.local.source.NoteDataSourceImpl
import com.sargis.khlopuzyan.data.repository.FakeNoteRepository
import com.sargis.khlopuzyan.data.repository.NoteRepositoryImpl
import com.sargis.khlopuzyan.domain.repository.NoteRepository
import org.koin.dsl.module

private val databaseModule = module {
    single<NoteDatabase> {
        NoteDatabase.getInstance(get())
    }
    single<NoteDao> {
        get<NoteDatabase>().noteDao()
    }
    single<NoteDataSource> {
        NoteDataSourceImpl(get())
    }
}

private val repositoryModule = module {
//    single<PixabayApi> { PixabayApiRetrofitBuilder.build() }
    single<NoteRepository> { NoteRepositoryImpl(get()) }
//    single<NoteRepository> { FakeNoteRepository() }
}

private val utilityModule = module {
}

val dataModule = listOf(databaseModule, repositoryModule, utilityModule)