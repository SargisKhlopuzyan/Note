package com.sargis.khlopuzyan.presentation.ui.note

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotesScreen(
    navController: NavController,
    notesViewModel: NotesViewModel = koinViewModel(),
) {
    val uiState by notesViewModel.uiState.collectAsStateWithLifecycle()

    Notes(navController)
}

@Composable
fun Notes(
    navController: NavController,
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { contentPadding ->

    }
}

@Preview(showBackground = true)
@Composable
fun NotesPreview() {
    Notes(rememberNavController())
}