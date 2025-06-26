package com.sargis.khlopuzyan.data.local.source

import com.sargis.khlopuzyan.data.local.dao.NoteDao
import com.sargis.khlopuzyan.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteDataStore {
    fun getNotes(): Flow<List<NoteEntity>>
    suspend fun getNoteById(id: Int): NoteEntity?
    suspend fun insertNote(node: NoteEntity): Int
    suspend fun deleteNote(node: NoteEntity): Int
}

class NoteDataStoreImpl(
    val dao: NoteDao,
) : NoteDataStore {
    override fun getNotes(): Flow<List<NoteEntity>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): NoteEntity? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(node: NoteEntity): Int {
        return dao.insertNote(node)
    }

    override suspend fun deleteNote(node: NoteEntity): Int {
        return dao.deleteNote(node)
    }
}