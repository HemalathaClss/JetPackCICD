package com.example.jetpacksample1.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Data layer for the Flow demo.
 *
 * This is where a Repository genuinely earns its place: it owns the data source
 * (here simulated with delays) and exposes it as a stream.
 *
 * `flow { ... }` builds a COLD stream:
 *  - The code inside the builder does NOT run when the Flow is created.
 *  - It starts running only when someone calls `.collect()`.
 *  - Each new collector runs the block again from the beginning (1, 2, 3 ...).
 * That "nothing happens until collected, and it restarts per collector" is the
 * defining property of a cold stream.
 */
class NumberRepository {

    fun numberStream(): Flow<Int> = flow {
        // This whole block only executes once a collector subscribes.
        for (i in 1..5) {
            delay(1000)   // pretend we are fetching from network / database
            emit(i)       // push the next value down to the collector
        }
    }
}