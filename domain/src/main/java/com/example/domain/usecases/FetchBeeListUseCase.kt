package com.example.domain.usecases

import com.example.common.Resource
import com.example.domain.BeeRaceRepository
import com.example.domain.models.Bee
import kotlinx.coroutines.flow.Flow

class FetchBeeListUseCase(private val repository: BeeRaceRepository) {
    operator fun invoke(): Flow<Resource<List<Bee>>> {
        return repository.fetchBeeList()
    }
}