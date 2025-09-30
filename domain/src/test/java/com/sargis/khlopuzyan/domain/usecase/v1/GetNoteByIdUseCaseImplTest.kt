package com.sargis.khlopuzyan.domain.usecase.v1

import com.google.common.truth.Truth
import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.repository.FakeNoteRepository
import com.sargis.khlopuzyan.domain.repository.NoteRepository
import com.sargis.khlopuzyan.domain.usecase.GetNoteById
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNoteByIdUseCaseImplTest {

    private lateinit var getNoteById: GetNoteById
    private lateinit var noteRepository: NoteRepository
    private val colors = listOf(1, 2, 3, 4, 5, 6)

    @Before
    fun setUp() {
        noteRepository = FakeNoteRepository()
//        getNoteByIdUseCase = GetNoteByIdUseCaseImpl(noteRepository)
        getNoteById = GetNoteById(noteRepository)

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
        }
        also {
            println("notesToInsert: $notesToInsert")
        }
        // As the list already sorted by timeStamp we need to shuffle it
        notesToInsert.shuffle()

        runBlocking {
            notesToInsert.forEach { note ->
                noteRepository.insertNote(note)
            }
        }
    }

    @Test
    fun `finding note with not existing id returns null`() = runBlocking {
//        val note = getNoteByIdUseCase.getNoteById(-1)
        val note = getNoteById(-1)
        Truth.assertThat(note).isNull()
    }

    @Test
    fun `finding note with existing id returns note`() = runBlocking {
//        val note = getNoteByIdUseCase.getNoteById(1)
        val note = getNoteById(1)
        Truth.assertThat(note).isNotNull()
    }
}