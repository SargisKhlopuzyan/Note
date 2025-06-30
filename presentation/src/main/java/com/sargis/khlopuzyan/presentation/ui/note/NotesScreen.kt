package com.sargis.khlopuzyan.presentation.ui.note

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sargis.khlopuzyan.domain.entity.Note
import com.sargis.khlopuzyan.domain.util.NoteOrder
import com.sargis.khlopuzyan.presentation.ui.common.OrderSection
import com.sargis.khlopuzyan.presentation.ui.navigation.NoteScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotesScreen(
    navController: NavController,
    notesViewModel: NotesViewModel = koinViewModel(),
) {
    val uiState by notesViewModel.uiState.collectAsStateWithLifecycle()

    Notes(
        navController, uiState,
        onToggleOrderSection = {
            notesViewModel.onEvent(NotesUiEvent.ToggleOrderSection)
        },
        onChangeOrder = { noteOrder ->
            notesViewModel.onEvent(NotesUiEvent.Order(noteOrder))
        },
        onDeleteNote = { note ->
            notesViewModel.onEvent(NotesUiEvent.DeleteNote(note))
        },
        onRestoreNote = {
            notesViewModel.onEvent(NotesUiEvent.RestoreNote)
        },
        onAddNote = {
            navController.navigate(NoteScreen.AddEditNoteScreen.route)
        }
    )
}

@Composable
fun Notes(
    navController: NavController,
    uiState: NotesUiState,
    onToggleOrderSection: () -> Unit,
    onChangeOrder: (NoteOrder) -> Unit,
    onDeleteNote: (Note) -> Unit,
    onRestoreNote: () -> Unit,
    onAddNote: () -> Unit,
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddNote()
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add note"
                )
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your notes",
                    style = MaterialTheme.typography.headlineMedium
                )

                IconButton(
                    onClick = onToggleOrderSection
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Sort,
                        contentDescription = "Sort"
                    )
                }
            }
            AnimatedVisibility(
                visible = uiState.orderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    noteOrder = uiState.noteOrder,
                    onOrderChange = { noteOrder ->
                        onChangeOrder(noteOrder)
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(uiState.notes) { note ->
                    NoteItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    NoteScreen.AddEditNoteScreen.route + "?noteId=${note.id}&noteColor=${note.color}"
                                )
                            },
                        note = note,
                        onDeleteClick = {
                            onDeleteNote(note)
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Note deleted",
                                    actionLabel = "Undo",
                                    duration = SnackbarDuration.Long
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    onRestoreNote()
                                }
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotesPreview() {
    Notes(
        rememberNavController(),
        NotesUiState(),
        onToggleOrderSection = {},
        onChangeOrder = {},
        onDeleteNote = {},
        onRestoreNote = {},
        onAddNote = {},
    )
}