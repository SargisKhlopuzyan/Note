package com.sargis.khlopuzyan.domain.usecase

import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.repository.NoteRepository
import com.sargis.khlopuzyan.domain.util.NoteOrder
import com.sargis.khlopuzyan.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetNotesUseCase {
    fun getNotes(noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)): Flow<List<Note>>
}

class GetNotesUseCaseImpl(
    val noteRepository: NoteRepository,
) : GetNotesUseCase {
    override fun getNotes(noteOrder: NoteOrder): Flow<List<Note>> {
        return noteRepository.getNotes().map { notes ->
            when (noteOrder.orderType) {
                OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedBy { it.timeStamp }
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                    }
                }

                OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timeStamp }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}

/**
 * Instead of interface and implementation we can just have this
 * */
//class GetNotesUseCase(
//    val noteRepository: NoteRepository,
//) {
//    operator fun invoke(noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)): Flow<List<Note>> {
//        return noteRepository.getNotes().map { notes ->
//            when (noteOrder.orderType) {
//                OrderType.Ascending -> {
//                    when (noteOrder) {
//                        is NoteOrder.Title -> notes.sortedBy { it.title }
//                        is NoteOrder.Date -> notes.sortedBy { it.timeStamp }
//                        is NoteOrder.Color -> notes.sortedBy { it.color }
//                    }
//                }
//
//                OrderType.Descending -> {
//                    when (noteOrder) {
//                        is NoteOrder.Title -> notes.sortedByDescending { it.title }
//                        is NoteOrder.Date -> notes.sortedByDescending { it.timeStamp }
//                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
//                    }
//                }
//            }
//        }
//    }
//}