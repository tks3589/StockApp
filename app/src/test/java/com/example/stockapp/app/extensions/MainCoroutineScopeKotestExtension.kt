package com.example.stockapp.app.extensions

import io.kotest.core.listeners.AfterTestListener
import io.kotest.core.listeners.BeforeTestListener
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

class MainCoroutineScopeKotestExtension(
    val dispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : BeforeTestListener, AfterTestListener {

    override suspend fun beforeTest(testCase: TestCase) {
        Dispatchers.setMain(dispatcher)
    }

    override suspend fun afterTest(testCase: TestCase, result: TestResult) {
        Dispatchers.resetMain()
    }
}
