package com.example.jetpacksample1.dependencyinjection

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Dependency Injection demo — the CONSUMER (ViewModel).
 *
 * Notice what this class does NOT do:
 *
 *     // ❌ WITHOUT DI — the ViewModel creates its own dependency:
 *     private val repository = RealGreetingRepository()
 *
 * That line looks harmless but it hard-wires the ViewModel to one concrete
 * class. You could never unit-test it with a fake, and switching the data
 * source would mean editing the ViewModel itself.
 *
 * Instead we use CONSTRUCTOR INJECTION: the dependency arrives as a constructor
 * parameter. The ViewModel simply declares "I need a GreetingRepository" and
 * trusts whoever creates it to provide one. This is the simplest, most testable
 * form of dependency injection — no framework (Hilt/Dagger) required.
 */
class GreetingViewModel(
    // The injected dependency. It is the INTERFACE type, so any implementation
    // (real or fake) can be passed in.
    private val repository: GreetingRepository
) : ViewModel() {

    // The greeting currently shown on screen.
    private val _greeting = MutableStateFlow("Press the button to get a greeting.")
    val greeting: StateFlow<String> = _greeting.asStateFlow()

    /**
     * Builds a greeting by delegating to the injected repository.
     * The ViewModel never knows (or cares) which implementation it received.
     */
    fun greet(name: String) {
        _greeting.value = repository.getGreeting(name)
    }
}