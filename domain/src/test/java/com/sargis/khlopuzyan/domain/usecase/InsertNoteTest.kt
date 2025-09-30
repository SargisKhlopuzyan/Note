package com.sargis.khlopuzyan.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.sargis.khlopuzyan.domain.entity.InvalidNoteException
import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.repository.FakeNoteRepository
import com.sargis.khlopuzyan.domain.repository.NoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class InsertNoteTest {

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
    fun `adding note with empty title throws InvalidNoteException`() {
        val note = Note(
            id = 1,
            title = "",
            content = "Content",
            timeStamp = System.currentTimeMillis(),
            color = colors.random()
        )
        val exception = assertThrows(InvalidNoteException::class.java) {
            runBlocking {
                assertThat(insertNote(note))
            }
        }

        assertThat(exception).hasMessageThat().isEqualTo("The title of the note can't be empty.")
    }

    @Test
    fun `adding note with empty content throws InvalidNoteException`() {
        val note = Note(
            id = 1,
            title = "Title",
            content = "",
            timeStamp = System.currentTimeMillis(),
            color = colors.random()
        )
        val exception = assertThrows(InvalidNoteException::class.java) {
            runBlocking {
                assertThat(insertNote(note))
            }
        }

        assertThat(exception).hasMessageThat().isEqualTo("The content of the note can't be empty.")
    }

    @Test
    fun `adding note with title and content adds in datastore`() {
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
            assertThat(savedNote).isNotNull()
            assertThat(savedNote?.id).isEqualTo(noteId)
        }
    }

    @Test
    fun `adding note with the same id updates the note in datastore`() {
        val noteId = 1
        val note1 = Note(
            id = noteId,
            title = "Title1",
            content = "Content1",
            timeStamp = System.currentTimeMillis(),
            color = colors.random()
        )

        val note2 = Note(
            id = noteId,
            title = "Title2",
            content = "Content2",
            timeStamp = System.currentTimeMillis(),
            color = colors.random()
        )

        runBlocking {
            insertNote(note1)
            var savedNote = getNoteById(noteId)
            assertThat(savedNote).isNotNull()
            assertThat(savedNote?.id).isEqualTo(noteId)

            insertNote(note2)
            savedNote = getNoteById(noteId)
            assertThat(savedNote).isNotNull()
            assertThat(savedNote?.id).isEqualTo(noteId)
            assertThat(savedNote?.title).isEqualTo(note2.title)
            assertThat(savedNote?.content).isEqualTo(note2.content)
        }
    }
}