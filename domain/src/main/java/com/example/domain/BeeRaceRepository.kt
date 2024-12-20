package com.example.domain

import com.example.common.Resource
import com.example.domain.models.Bee
import kotlinx.coroutines.flow.Flow

interface BeeRaceRepository {
    fun fetchRaceDuration(): Flow<Int>
    fun fetchBeeList(): Flow<Resource<List<Bee>>>
}