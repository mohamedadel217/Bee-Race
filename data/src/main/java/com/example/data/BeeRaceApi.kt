package com.example.data

import com.example.data.models.DurationResponse
import com.example.data.models.RaceStatusResponse
import retrofit2.Response
import retrofit2.http.GET

interface BeeRaceApi {
    @GET("bees/race/duration")
    suspend fun getRaceDuration(): Response<DurationResponse>

    @GET("bees/race/status")
    suspend fun getRaceStatus(): Response<RaceStatusResponse>
}