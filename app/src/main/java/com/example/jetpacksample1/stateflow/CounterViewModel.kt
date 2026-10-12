package com.example.jetpacksample1.stateflow

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Immutable snapshot of everything the screen needs to draw itself.
 * Modelling UI as a single state object is the idiomatic StateFlow pattern.
 */
data class CounterUiState(
    val count: Int = 0,
    val isEven: Boolean = true
)

/**
 * StateFlow demo — ViewModel as the state holder.
 *
 * StateFlow is a *hot*, state-holding stream:
 *  - It ALWAYS has a current value (you must give it an initial one).
 *  - New collectors immediately get the latest value (conflated state).
 *  - It only emits when the value actually changes (distinct-until-changed).
 *
 * Compared to LiveData it is a pure-Kotlin (non-Android) equivalent and is the
 * modern recommendation for exposing UI state from a ViewModel.
 *
 * No Repository: this is in-memory UI state with no external data source.
 */
class CounterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CounterUiState())
    val uiState: StateFlow<CounterUiState> = _uiState.asStateFlow()

    fun increment() = updateCount(+1)
    fun decrement() = updateCount(-1)
    fun reset() = _uiState.update { CounterUiState() }

    private fun updateCount(delta: Int) {
        // update { } reads the current value and atomically writes the new one.
        _uiState.update { current ->
            val newCount = current.count + delta
            current.copy(count = newCount, isEven = newCount % 2 == 0)
        }
    }
}