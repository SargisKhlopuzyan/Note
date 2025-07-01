package com.sargis.khlopuzyan.domain.usecase

import com.google.common.truth.Truth
import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.repository.FakeNoteRepository
import com.sargis.khlopuzyan.domain.util.NoteOrder
import com.sargis.khlopuzyan.domain.util.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesUseCaseImplTest {

    private lateinit var getNotesUseCase: GetNotesUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository
    val colors = listOf(1, 2, 3, 4, 5, 6)

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getNotesUseCase = GetNotesUseCaseImpl(fakeNoteRepository)

        val notesToInsert = mutableListOf<Note>()

        ('a'..'z').forEachIndexed { index, c ->
            val note = Note(
                id = index,
                title = c.toString(),
                content = c.toString(),
                timeStamp = System.currentTimeMillis(),
                color = colors.random()
            )
            notesToInsert.add(note)
            notesToInsert.add(note.copy(id = 100 * index))
        }
        also {
            println("notesToInsert: $notesToInsert")
        }
        // As the list already sorted by timeStamp we need to shuffle it
        notesToInsert.shuffle()

        runBlocking {
            notesToInsert.forEach { note ->
                fakeNoteRepository.insertNote(note)
            }
        }
    }


    // Title
    @Test
    fun `Order notes by title ascending, correct order`() = runBlocking {
        val notes = getNotesUseCase.getNotes(NoteOrder.Title(OrderType.Ascending)).first()
        for (i in 0..notes.size - 2) {
//            Truth.assertThat(notes[i].title).isLessThan(notes[i + 1].title)
            Truth.assertThat(notes[i].title).isAtMost(notes[i + 1].title)
        }
    }

    @Test
    fun `Order notes by title descending, correct order`() = runBlocking {
        val notes = getNotesUseCase.getNotes(NoteOrder.Title(OrderType.Descending)).first()
        for (i in 0..notes.size - 2) {
//            Truth.assertThat(notes[i].title).isGreaterThan(notes[i + 1].title)
            Truth.assertThat(notes[i].title).isAtLeast(notes[i + 1].title)
        }
    }

    // Date
    @Test
    fun `Order notes by date ascending, correct order`() = runBlocking {
        val notes = getNotesUseCase.getNotes(NoteOrder.Date(OrderType.Ascending)).first()
        for (i in 0..notes.size - 2) {
            Truth.assertThat(notes[i].timeStamp).isAtMost(notes[i + 1].timeStamp)
        }
    }

    @Test
    fun `Order notes by date descending, correct order`() = runBlocking {
        val notes = getNotesUseCase.getNotes(NoteOrder.Date(OrderType.Descending)).first()
        for (i in 0..notes.size - 2) {
            Truth.assertThat(notes[i].timeStamp).isAtLeast(notes[i + 1].timeStamp)
        }
    }

    // Color
    @Test
    fun `Order notes by color ascending, correct order`() = runBlocking {
        val notes = getNotesUseCase.getNotes(NoteOrder.Color(OrderType.Ascending)).first()
        for (i in 0..notes.size - 2) {
            Truth.assertThat(notes[i].color).isAtMost(notes[i + 1].color)
        }
    }

    @Test
    fun `Order notes by color descending, correct order`() = runBlocking {
        val notes = getNotesUseCase.getNotes(NoteOrder.Color(OrderType.Descending)).first()
        for (i in 0..notes.size - 2) {
            Truth.assertThat(notes[i].color).isAtLeast(notes[i + 1].color)
        }
    }
}