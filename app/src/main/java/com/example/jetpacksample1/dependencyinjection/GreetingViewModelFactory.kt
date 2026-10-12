package com.example.jetpacksample1.dependencyinjection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Dependency Injection demo — the INJECTOR (wiring).
 *
 * `GreetingViewModel` has a non-empty constructor, but the Android framework
 * only knows how to create ViewModels with an EMPTY constructor. So we need a
 * factory to tell the framework HOW to build it — and this factory is exactly
 * the place where the dependency gets injected.
 *
 * This factory plays the role that Hilt/Dagger would play in a larger app: it
 * is the single spot that decides which concrete `GreetingRepository` the
 * ViewModel receives. To swap the data source for the whole feature, you change
 * only this one line — nothing in the ViewModel or UI has to change.
 *
 * @param repository the dependency to inject. Defaults to the real one for app
 *        use, but a test can pass in a fake implementation instead.
 */
class GreetingViewModelFactory(
    private val repository: GreetingRepository = RealGreetingRepository()
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Construct the ViewModel and hand it the dependency. This is the moment
        // of "injection".
        if (modelClass.isAssignableFrom(GreetingViewModel::class.java)) {
            return GreetingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}