package com.sargis.khlopuzyan.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sargis.khlopuzyan.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NoteDao {

    @Query("SELECT * FROM note")
    abstract fun getNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note WHERE id = :id")
    abstract suspend fun getNoteById(id: Int): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertNote(node: NoteEntity): Long

    @Delete
    abstract suspend fun deleteNote(node: NoteEntity): Int
}