package com.example.domain

import com.example.common.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler

class TestDispatcherProvider : DispatcherProvider {
    private val testScheduler = TestCoroutineScheduler()
    override val io: CoroutineDispatcher = StandardTestDispatcher(testScheduler)
    override val default: CoroutineDispatcher = StandardTestDispatcher(testScheduler)
    override val main: CoroutineDispatcher = StandardTestDispatcher(testScheduler)
}
