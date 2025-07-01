package com.sargis.khlopuzyan.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sargis.khlopuzyan.domain.entity.Note

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val content: String,
    val timeStamp: Long,
    val color: Int,
)

fun List<NoteEntity>.toNotes() = map { noteEntity ->
    noteEntity.toNote()
}

fun NoteEntity.toNote() = Note(
    id = id,
    title = title,
    content = content,
    timeStamp = timeStamp,
    color = color,
)

fun Note.toNoteEntity() = NoteEntity(
    id = id,
    title = title,
    content = content,
    timeStamp = timeStamp,
    color = color,
)