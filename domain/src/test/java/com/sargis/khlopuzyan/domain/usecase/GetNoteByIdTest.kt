package com.sargis.khlopuzyan.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.repository.FakeNoteRepository
import com.sargis.khlopuzyan.domain.repository.NoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNoteByIdTest {

    private lateinit var noteRepository: NoteRepository
    private lateinit var insertNote: InsertNote
    private lateinit var getNoteById: GetNoteById
    private val colors = listOf(1, 2, 3, 4, 5, 6)

    @Before
    fun setUp() {
        noteRepository = FakeNoteRepository()
        insertNote = InsertNote(noteRepository)
        getNoteById = GetNoteById(noteRepository)
    }

    @Test
    fun `inserting note with title and content saves in datastore`() {
        val noteId = 1
        val note = Note(
            id = noteId,
            title = "Title",
            content = "Content",
            timeStamp = System.currentTimeMillis(),
            color = colors.random()
        )
        runBlocking {
            insertNote(note)
            val savedNote = getNoteById(noteId)
            assertThat(savedNote?.id).isEqualTo(noteId)
            assertThat(savedNote?.title).isEqualTo(note.title)
            assertThat(savedNote?.content).isEqualTo(note.content)
        }
    }
}