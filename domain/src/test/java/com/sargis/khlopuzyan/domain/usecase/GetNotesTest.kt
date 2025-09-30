package com.sargis.khlopuzyan.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.repository.FakeNoteRepository
import com.sargis.khlopuzyan.domain.repository.NoteRepository
import com.sargis.khlopuzyan.domain.util.NoteOrder
import com.sargis.khlopuzyan.domain.util.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesTest {

    private lateinit var noteRepository: NoteRepository
    private lateinit var getNotes: GetNotes
    private val colors = listOf(1, 2, 3, 4, 5, 6)

    @Before
    fun setUp() {
        noteRepository = FakeNoteRepository()
        getNotes = GetNotes(noteRepository)

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    id = index,
                    title = c.toString(),
                    content = c.toString(),
                    timeStamp = System.currentTimeMillis(),
                    color = colors.random()
                )
            )
        }

        notesToInsert.shuffle()

        runBlocking {
            notesToInsert.forEach {
                noteRepository.insertNote(it)
            }
        }
    }

    @Test
    fun `Order notes by title ascending, correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Title(OrderType.Ascending)).first()
        for (i in 0 until notes.size - 2) {
            assertThat(notes[i].title).isAtMost(notes[i + 1].title)
        }
    }

    @Test
    fun `Order notes by title descending, correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Title(OrderType.Descending)).first()
        for (i in 0 until notes.size - 2) {
            assertThat(notes[i].title.lowercase()).isAtLeast(notes[i + 1].title.lowercase())
        }
    }

    @Test
    fun `Order notes by color ascending, correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Color(OrderType.Ascending)).first()
        for (i in 0 until notes.size - 2) {
            assertThat(notes[i].color).isAtMost(notes[i + 1].color)
        }
    }

    @Test
    fun `Order notes by color descending, correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Color(OrderType.Descending)).first()
        for (i in 0 until notes.size - 2) {
            assertThat(notes[i].color).isAtLeast(notes[i + 1].color)
        }
    }

    @Test
    fun `Order notes by date ascending, correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Date(OrderType.Ascending)).first()
        for (i in 0 until notes.size - 2) {
            assertThat(notes[i].timeStamp).isAtMost(notes[i + 1].timeStamp)
        }
    }

    @Test
    fun `Order notes by date descending, correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Date(OrderType.Descending)).first()
        for (i in 0 until notes.size - 2) {
            assertThat(notes[i].timeStamp).isAtLeast(notes[i + 1].timeStamp)
        }
    }
}