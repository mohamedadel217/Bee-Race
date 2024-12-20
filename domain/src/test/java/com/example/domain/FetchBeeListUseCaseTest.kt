package com.example.domain

import com.example.common.Resource
import com.example.domain.models.Bee
import com.example.domain.usecases.FetchBeeListUseCase
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
class FetchBeeListUseCaseTest {

    private lateinit var fetchBeeListUseCase: FetchBeeListUseCase
    private val repository: BeeRaceRepository = mockk()

    private val dispatcherProvider = TestDispatcherProvider()

    @Before
    fun setup() {
        fetchBeeListUseCase = FetchBeeListUseCase(repository)
    }

    @Test
    fun `invoke fetchBeeList returns success resource`() = runTest(dispatcherProvider.io) {
        // Mock repository behavior
        val beeList = listOf(Bee("Bee1", "#FFCC00"), Bee("Bee2", "#FF9900"))
        coEvery { repository.fetchBeeList() } returns flowOf(Resource.Success(beeList))

        // Execute use case
        val result = fetchBeeListUseCase().toList()

        // Verify results
        assertEquals(1, result.size)
        assert(result.first() is Resource.Success)
        assertEquals(beeList, (result.first() as Resource.Success).data)
        coVerify(exactly = 1) { repository.fetchBeeList() }
    }

    @Test
    fun `invoke fetchBeeList returns error resource`() = runTest(dispatcherProvider.io) {
        // Mock repository behavior
        val errorMessage = "Captcha required"
        coEvery { repository.fetchBeeList() } returns flowOf(Resource.Error(errorMessage))

        // Execute use case
        val result = fetchBeeListUseCase().toList()

        // Verify results
        assertEquals(1, result.size)
        assert(result.first() is Resource.Error)
        assertEquals(errorMessage, (result.first() as Resource.Error).message)
        coVerify(exactly = 1) { repository.fetchBeeList() }
    }
}
