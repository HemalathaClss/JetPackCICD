package com.example.jetpacksample1.sharedflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * One-time UI events. Unlike state, an event should be handled exactly once
 * (show a toast, navigate, etc.) and then be gone.
 */
sealed interface UiEvent {
    data class ShowToast(val message: String) : UiEvent
    data class Navigate(val route: String) : UiEvent
}

/**
 * SharedFlow demo — ViewModel for event-driven communication.
 *
 * SharedFlow is a *hot* stream with NO state:
 *  - It has no "current value"; there is nothing to read.
 *  - With the default replay = 0, a collector only receives events emitted
 *    AFTER it started collecting — past events are not replayed.
 * That makes it the right tool for one-time events (toast / navigation /
 * snackbar). If you used StateFlow for these, the event would be re-delivered
 * on every config change (because StateFlow retains its last value).
 */
class EventViewModel : ViewModel() {

    private val _events = MutableSharedFlow<UiEvent>()   // replay = 0 → no retention
    val events: SharedFlow<UiEvent> = _events.asSharedFlow()

    private var clickCount = 0

    fun onShowToastClick() {
        viewModelScope.launch {
            clickCount++
            _events.emit(UiEvent.ShowToast("Toast event #$clickCount"))
        }
    }

    fun onNavigateClick() {
        viewModelScope.launch {
            _events.emit(UiEvent.Navigate("details_screen"))
        }
    }
}