package com.sargis.khlopuzyan.presentation.ui.addEditNote

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.sargis.khlopuzyan.presentation.util.NoteUtil.noteColors
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteId: Int?,
    noteColor: Int?,
    addEditNoteViewModel: AddEditNoteViewModel = koinViewModel(),
) {
    val uiState by addEditNoteViewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    noteId?.let {
        addEditNoteViewModel.onEvent(AddEditNoteUiEvent.SetNoteId(noteId))
    }

    LaunchedEffect(true) {
        addEditNoteViewModel.showToastEventFlow.collectLatest { event ->
            when (event) {
                UICallbackEvent.SaveNote -> navController.navigateUp()
                is UICallbackEvent.ShowSnakebar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    AddEditNote(
        uiState,
        noteColor,
        onChangeColor = { color ->
            addEditNoteViewModel.onEvent(AddEditNoteUiEvent.ChangeColor(color))
        },
        onTitleValueChange = {
            addEditNoteViewModel.onEvent(AddEditNoteUiEvent.EnteredTitle(it))
        },
        onTitleFocusChange = {
            addEditNoteViewModel.onEvent(AddEditNoteUiEvent.ChangeTitleFocus(it))
        },
        onContentValueChange = {
            addEditNoteViewModel.onEvent(AddEditNoteUiEvent.EnteredContent(it))
        },
        onContentFocusChange = {
            addEditNoteViewModel.onEvent(AddEditNoteUiEvent.ChangeContentFocus(it))
        },
        onSaveNote = {
            addEditNoteViewModel.onEvent(AddEditNoteUiEvent.SaveNote)
        }
    )
}

@Composable
fun AddEditNote(
    uiState: AddEditNoteUiState,
    noteColor: Int?,
    onChangeColor: (Int) -> Unit,
    onTitleValueChange: (String) -> Unit,
    onTitleFocusChange: (FocusState) -> Unit,
    onContentValueChange: (String) -> Unit,
    onContentFocusChange: (FocusState) -> Unit,
    onSaveNote: () -> Unit,
) {
    val noteTitleState = uiState.noteTitleState.value
    val noteContentState = uiState.noteContentState.value
    val noteColorState = uiState.noteColorState.intValue

    val noteBackgroundAnimatable = remember {
        Animatable(Color(noteColor ?: noteColorState))
    }

    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onSaveNote()
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save note"
                )
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(contentPadding)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                noteColors().forEach { noteColor ->
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(16.dp, CircleShape)
                            .clip(CircleShape)
                            .background(Color(noteColor))
                            .border(
                                width = 3.dp,
                                color = if (noteColorState == noteColor) {
                                    Color.Black
                                } else {
                                    Color.Transparent
                                },
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(noteColor),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                onChangeColor(noteColor)
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TransparentHintTextField(
                text = noteTitleState.text,
                hint = noteTitleState.hint,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                onValueChange = {
                    onTitleValueChange(it)
                },
                onFocusChange = {
                    onTitleFocusChange(it)
                },
                isHintVisible = noteTitleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            TransparentHintTextField(
                text = noteContentState.text,
                hint = noteContentState.hint,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                onValueChange = {
                    onContentValueChange(it)
                },
                onFocusChange = {
                    onContentFocusChange(it)
                },
                isHintVisible = noteContentState.isHintVisible,
                singleLine = false,
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddEditNotePreview() {
    AddEditNote(
        AddEditNoteUiState(),
        noteColor = null,
        onChangeColor = {},
        onTitleValueChange = {},
        onTitleFocusChange = {},
        onContentValueChange = {},
        onContentFocusChange = {},
        onSaveNote = {}
    )
}