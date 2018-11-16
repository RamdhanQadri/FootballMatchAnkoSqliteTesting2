package rqk.footballmatchankosqlitetesting

import rqk.footballmatchankosqlitetesting.utils.CoroutineContextProvider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

class TestContextProvider: CoroutineContextProvider() {
    @ExperimentalCoroutinesApi
    override val main: CoroutineContext = Dispatchers.Unconfined
}