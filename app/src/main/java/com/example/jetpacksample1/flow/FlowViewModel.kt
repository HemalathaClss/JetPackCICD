package com.example.jetpacksample1.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Flow demo — ViewModel.
 *
 * It asks the Repository for a cold Flow and starts collecting it inside
 * `viewModelScope` (so collection is cancelled automatically when the ViewModel
 * is cleared). Collected values are accumulated into a list that the UI renders.
 *
 * Press "Start" repeatedly to see the cold behaviour: the list resets and the
 * numbers tick in 1..5 from scratch every single time, because each collect()
 * re-runs the flow builder.
 */
class FlowViewModel : ViewModel() {

    private val repository = NumberRepository()

    // Latest list of received numbers, for the UI to display.
    private val _numbers = MutableStateFlow<List<Int>>(emptyList())
    val numbers: StateFlow<List<Int>> = _numbers.asStateFlow()

    // Whether a collection is currently in progress (drives the button state).
    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    fun startCollecting() {
        viewModelScope.launch {
            _numbers.value = emptyList()
            _isRunning.value = true

            // .collect() is what actually triggers the cold flow to start emitting.
            repository.numberStream().collect { value ->
                _numbers.value = _numbers.value + value
            }

            _isRunning.value = false
        }
    }
}