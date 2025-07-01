package com.sargis.khlopuzyan.data.local.dao

import com.google.common.truth.Truth
import com.sargis.khlopuzyan.data.local.entity.NoteEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class TestNoteDao : KoinTest {

    private val noteDao: NoteDao by inject()
//    private val database: Database by inject()

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
//        stopKoin()
//        database.close()
    }

    @Test
    fun `insertingNote_addsNoteInLocalDb`() = runTest {
        val noteEntity = NoteEntity(
            id = 1,
            title = "Title",
            content = "Content",
            timeStamp = System.currentTimeMillis(),
            color = 1
        )
        noteDao.insertNote(noteEntity)

        Truth.assertThat(noteDao.getNoteById(1)).isNotNull()
    }
}