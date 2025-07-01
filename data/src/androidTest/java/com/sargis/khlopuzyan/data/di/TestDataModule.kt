package com.sargis.khlopuzyan.data.di

import com.sargis.khlopuzyan.data.local.dao.NoteDao
import com.sargis.khlopuzyan.data.local.db.NoteDatabase
import com.sargis.khlopuzyan.data.local.source.FakeAndroidNoteDataSource
import com.sargis.khlopuzyan.data.local.source.NoteDataSource
import com.sargis.khlopuzyan.data.local.source.NoteDataSourceImpl
import com.sargis.khlopuzyan.data.repository.NoteRepositoryImpl
import com.sargis.khlopuzyan.domain.repository.NoteRepository
import org.koin.dsl.module

private val testDatabaseModule = module {
    single<NoteDatabase> {
        NoteDatabase.getTestInstance(get())
    }
    single<NoteDao> {
        get<NoteDatabase>().noteDao()
    }
    single<NoteDataSource> {
//        FakeAndroidNoteDataSource()
        NoteDataSourceImpl(get())
    }
}

private val repositoryModule = module {
    single<NoteRepository> { NoteRepositoryImpl(get()) }
}

val testDataModule = listOf(testDatabaseModule, repositoryModule)