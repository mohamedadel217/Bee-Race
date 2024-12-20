package com.example.domain.usecases

import com.example.domain.BeeRaceRepository
import kotlinx.coroutines.flow.Flow

class FetchRaceDurationUseCase(private val repository: BeeRaceRepository) {
    operator fun invoke(): Flow<Int> {
        return repository.fetchRaceDuration()
    }
}