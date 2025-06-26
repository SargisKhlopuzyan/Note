package com.sargis.khlopuzyan.data.local.entity

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sargis.khlopuzyan.domain.entity.Note

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val title: String,
    val content: String,
    val timeStamp: Long,
    val color: Int,
) {
    companion object {
        val RedOrange = Color.argb(255, 83, 73, 255)
        val LightGreen = Color.argb(144, 245, 100, 255)
        val Violet = Color.argb(146, 100, 245, 255)
        val BabyBlue = Color.argb(100, 206, 245, 255)
        val RedPink = Color.argb(245, 100, 144, 255)
        val noteColor = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

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