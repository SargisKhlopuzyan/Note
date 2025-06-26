package com.sargis.khlopuzyan.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<S : UIState, in E : UIEvent> : ViewModel() {

    protected abstract val _uiState: MutableStateFlow<S>

    abstract fun onEvent(event: E)

    open val uiState: StateFlow<S>
        get() = _uiState

    protected fun updateUiState(newState: (S) -> S) {
        _uiState.update(newState)
    }
}