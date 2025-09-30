package com.sargis.khlopuzyan.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.repository.FakeNoteRepository
import com.sargis.khlopuzyan.domain.repository.NoteRepository
import kotlinx.coroutines.runBlocking
import org.jetbrains.annotations.TestOnly
import org.junit.Before
import org.junit.Test

class DeleteNoteTest {

    private lateinit var noteRepository: NoteRepository
    private lateinit var insertNote: InsertNote
    private lateinit var getNoteById: GetNoteById
    private lateinit var deleteNote: DeleteNote
    private val colors = listOf(1, 2, 3, 4, 5, 6)

    @Before
    fun setUp() {
        noteRepository = FakeNoteRepository()
        insertNote = InsertNote(noteRepository)
        getNoteById = GetNoteById(noteRepository)
        deleteNote = DeleteNote(noteRepository)
    }

    @Test
    fun `deleting existing note will remove note from datastore`() {
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
            var savedNote = getNoteById(noteId)
            assertThat(savedNote).isNotNull()
            deleteNote(note)
            savedNote = getNoteById(noteId)
            assertThat(savedNote).isNull()
        }
    }
}