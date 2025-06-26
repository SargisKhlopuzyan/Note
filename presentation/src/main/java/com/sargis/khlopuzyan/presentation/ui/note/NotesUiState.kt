package com.sargis.khlopuzyan.presentation.ui.note

import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.util.NoteOrder
import com.sargis.khlopuzyan.domain.util.OrderType
import com.sargis.khlopuzyan.presentation.base.UIState

data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val orderSectionVisible: Boolean = false,
) : UIState