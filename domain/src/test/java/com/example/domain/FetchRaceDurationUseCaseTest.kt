package com.example.domain

import com.example.domain.usecases.FetchRaceDurationUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FetchRaceDurationUseCaseTest {

    private lateinit var fetchRaceDurationUseCase: FetchRaceDurationUseCase
    private val repository: BeeRaceRepository = mockk()

    private val dispatcherProvider = TestDispatcherProvider()

    @Before
    fun setup() {
        fetchRaceDurationUseCase = FetchRaceDurationUseCase(repository)
    }

    @Test
    fun `invoke fetchRaceDuration returns correct duration`() = runTest(dispatcherProvider.io) {
        // Mock repository behavior
        val duration = 30
        coEvery { repository.fetchRaceDuration() } returns flowOf(duration)

        // Execute use case
        val result = fetchRaceDurationUseCase().toList()

        // Verify results
        assertEquals(listOf(duration), result)
        coVerify(exactly = 1) { repository.fetchRaceDuration() }
    }
}
