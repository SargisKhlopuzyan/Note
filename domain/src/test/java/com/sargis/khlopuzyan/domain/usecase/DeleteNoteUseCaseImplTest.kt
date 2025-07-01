package com.sargis.khlopuzyan.domain.usecase

import com.google.common.truth.Truth
import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.repository.FakeNoteRepository
import com.sargis.khlopuzyan.domain.repository.NoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteNoteUseCaseImplTest {

    private lateinit var deleteNoteUseCase: DeleteNoteUseCase
    private lateinit var getNoteByIdUseCase: GetNoteByIdUseCase
    private lateinit var noteRepository: NoteRepository
    private val colors = listOf(1, 2, 3, 4, 5, 6)
    private val noteId = 0

    @Before
    fun setUp() {
        noteRepository = FakeNoteRepository()
        deleteNoteUseCase = DeleteNoteUseCaseImpl(noteRepository)
        getNoteByIdUseCase = GetNoteByIdUseCaseImpl(noteRepository)

        val note = Note(
            id = noteId,
            title = "Title",
            content = "Content",
            timeStamp = System.currentTimeMillis(),
            color = colors.random()
        )

        runBlocking {
            noteRepository.insertNote(note)
        }
    }

    @Test
    fun `deleting note will remove note from the list`() = runBlocking {
        val existingNote = getNoteByIdUseCase.getNoteById(noteId)
        Truth.assertThat(existingNote).isNotNull()
        deleteNoteUseCase.deleteNote(existingNote!!)
        val deletedNote = getNoteByIdUseCase.getNoteById(noteId)
        Truth.assertThat(deletedNote).isNull()
    }
}