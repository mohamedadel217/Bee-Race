package com.example.data

import com.example.common.Resource
import com.example.data.models.BeeResponse
import com.example.data.models.DurationResponse
import com.example.data.models.RaceStatusResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class BeeRaceRepositoryImplTest {

    private lateinit var repository: BeeRaceRepositoryImpl
    private val api: BeeRaceApi = mockk()
    private val dispatcherProvider = TestDispatcherProvider()

    @Before
    fun setup() {
        repository = BeeRaceRepositoryImpl(api, dispatcherProvider)
    }

    @Test
    fun `fetchRaceDuration returns correct duration`() = runTest(dispatcherProvider.io) {
        // Mock API response
        val durationResponse = DurationResponse(30)
        coEvery { api.getRaceDuration() } returns Response.success(durationResponse)

        // Execute repository method
        val result = repository.fetchRaceDuration().toList()

        // Verify results
        assertEquals(listOf(30), result)
        coVerify(exactly = 1) { api.getRaceDuration() }
    }

    @Test
    fun `fetchRaceDuration returns 0 on error`() = runTest(dispatcherProvider.io) {
        // Mock API error
        coEvery { api.getRaceDuration() } returns Response.error(
            500,
            "Internal Server Error".toResponseBody("text/plain".toMediaType())
        )

        // Execute repository method
        val result = repository.fetchRaceDuration().toList()

        // Verify results
        assertEquals(listOf(0), result) // Verify fallback to 0
        coVerify(exactly = 1) { api.getRaceDuration() }
    }

    @Test
    fun `fetchRaceDuration returns 0 on exception`() = runTest(dispatcherProvider.io) {
        // Mock exception
        coEvery { api.getRaceDuration() } throws Exception("Network error")

        // Execute repository method
        val result = repository.fetchRaceDuration().toList()

        // Verify results
        assertEquals(listOf(0), result) // Verify fallback to 0
        coVerify(exactly = 1) { api.getRaceDuration() }
    }

    @Test
    fun `fetchBeeList returns success resource`() = runTest(dispatcherProvider.io) {
        // Mock API response
        val beeResponseList = listOf(BeeResponse("Bee1", "#FFCC00"), BeeResponse("Bee2", "#FF9900"))
        val raceStatusResponse = RaceStatusResponse(beeList = beeResponseList)
        coEvery { api.getRaceStatus() } returns Response.success(raceStatusResponse)

        // Execute repository method
        val result = repository.fetchBeeList().toList()

        // Verify results
        assertEquals(2, result.size) // Loading + Success
        assert(result.first() is Resource.Loading)
        assert(result[1] is Resource.Success)
        val beeList = (result[1] as Resource.Success).data
        assertEquals(2, beeList.size)
        assertEquals("Bee1", beeList[0].name)
        coVerify(exactly = 1) { api.getRaceStatus() }
    }

    @Test
    fun `fetchBeeList returns generic error on other errors`() = runTest(dispatcherProvider.io) {
        // Mock API error
        coEvery { api.getRaceStatus() } returns Response.error(
            500,
            "Internal Server Error".toResponseBody("text/plain".toMediaType())
        )

        // Execute repository method
        val result = repository.fetchBeeList().toList()

        // Verify results
        assertEquals(2, result.size) // Loading + Error
        assert(result.first() is Resource.Loading)
        assert(result[1] is Resource.Error)
        val error = result[1] as Resource.Error
        assertEquals("Error: 500", error.message)
        coVerify(exactly = 1) { api.getRaceStatus() }
    }
}

