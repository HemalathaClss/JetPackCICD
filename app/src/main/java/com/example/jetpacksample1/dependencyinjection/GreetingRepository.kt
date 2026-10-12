package com.example.jetpacksample1.dependencyinjection

/**
 * Dependency Injection demo — the DEPENDENCY (data layer).
 *
 * The key idea of Dependency Injection (DI) is: a class should NOT create the
 * things it depends on. Instead those things are handed ("injected") to it from
 * the outside. To make that possible we depend on an INTERFACE, not a concrete
 * class.
 *
 * Why an interface?
 *  - The ViewModel only needs to know "something can give me a greeting".
 *  - It does not care whether that greeting comes from the network, a database,
 *    or a hard-coded string.
 *  - At runtime we inject the real implementation; in a unit test we can inject
 *    a fake one. That swap-ability is the whole point of DI.
 */
interface GreetingRepository {
    fun getGreeting(name: String): String
}

/**
 * The REAL implementation that the app uses at runtime.
 *
 * In a production app this might call Retrofit or read from Room. Here it just
 * builds a string so the demo stays focused on the injection wiring.
 */
class RealGreetingRepository : GreetingRepository {
    override fun getGreeting(name: String): String {
        val cleanName = name.trim().ifEmpty { "stranger" }
        return "Hello, $cleanName! 👋 (this greeting was provided by an injected repository)"
    }
}