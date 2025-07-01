package com.sargis.khlopuzyan.domain.usecase

import com.google.common.truth.Truth
import com.sargis.khlopuzyan.domain.entity.InvalidNoteException
import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.repository.FakeNoteRepository
import com.sargis.khlopuzyan.domain.repository.NoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class InsertNoteUseCaseImplTest {

    private lateinit var insertNoteUseCase: InsertNoteUseCase
    private lateinit var repository: NoteRepository
    val colors = listOf(1, 2, 3, 4, 5, 6)

    @Before
    fun setUp() {
        repository = FakeNoteRepository()
        insertNoteUseCase = InsertNoteUseCaseImpl(repository)
    }

    @Test
    fun `inserting note with empty title throws InvalidNoteException`() = runBlocking {
        val note = Note(
            id = 0,
            title = "",
            content = "Content",
            timeStamp = System.currentTimeMillis(),
            color = colors.random()
        )
        try {
            insertNoteUseCase.insertNote(note)
        } catch (e: InvalidNoteException) {
            Truth.assertThat(e).hasMessageThat().isEqualTo("The title of the note can't be empty.");
        }
    }

    @Test
    fun `inserting note with empty content throws InvalidNoteException`() = runBlocking {
        val note = Note(
            id = 0,
            title = "Title",
            content = "",
            timeStamp = System.currentTimeMillis(),
            color = colors.random()
        )
        try {
            insertNoteUseCase.insertNote(note)
        } catch (e: InvalidNoteException) {
            Truth.assertThat(e).hasMessageThat()
                .isEqualTo("The content of the note can't be empty.");
        }
    }

    @Test
    fun `inserting note with not an empty title and content doesn't throw InvalidNoteException`() =
        runBlocking {
            val note = Note(
                id = 0,
                title = "Title",
                content = "Content",
                timeStamp = System.currentTimeMillis(),
                color = colors.random()
            )
            insertNoteUseCase.insertNote(note)
        }
}