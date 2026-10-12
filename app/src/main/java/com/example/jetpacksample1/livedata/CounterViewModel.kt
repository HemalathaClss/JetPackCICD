package com.example.jetpacksample1.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * LiveData demo — ViewModel (state holder).
 *
 * LiveData is a *lifecycle-aware* observable data holder:
 *  - It always keeps the latest value, so a new/returning observer immediately
 *    gets the current state.
 *  - It only delivers updates while the observer is in an active lifecycle state
 *    (STARTED / RESUMED), so you never touch a destroyed view — no leaks.
 *
 * No Repository is used here on purpose: the counter is pure in-memory UI state,
 * so a data layer would add nothing. Repositories are added only where there is
 * an actual data source to talk to (see the `flow` package).
 */
class CounterViewModel : ViewModel() {

    // Mutable inside the ViewModel, exposed as read-only LiveData to the UI.
    // This encapsulation makes the ViewModel the single source of truth.
    private val _count = MutableLiveData(0)
    val count: LiveData<Int> = _count

    fun increment() {
        _count.value = (_count.value ?: 0) + 1
    }

    fun decrement() {
        _count.value = (_count.value ?: 0) - 1
    }

    fun reset() {
        _count.value = 0
    }
}