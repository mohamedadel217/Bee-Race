package com.example.feature.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Resource
import com.example.domain.models.Bee
import com.example.domain.usecases.FetchBeeListUseCase
import com.example.domain.usecases.FetchRaceDurationUseCase
import com.example.feature.models.RaceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RaceViewModel(
    private val fetchRaceDurationUseCase: FetchRaceDurationUseCase,
    private val fetchBeeListUseCase: FetchBeeListUseCase
) : ViewModel() {

    private val _raceScreenState = MutableStateFlow<RaceScreenState>(RaceScreenState.Loading)
    val raceScreenState: StateFlow<RaceScreenState> = _raceScreenState

    init {
        observeRaceData()
    }

    internal fun observeRaceData() {
        viewModelScope.launch {
            combine(
                fetchRaceDurationUseCase(),
                fetchBeeListUseCase()
            ) { duration, beeListResource ->
                when (beeListResource) {
                    is Resource.Loading -> RaceScreenState.Loading
                    is Resource.Success -> RaceScreenState.Data(
                        duration = duration,
                        bees = beeListResource.data
                    )
                    is Resource.Error -> RaceScreenState.Error(
                        message = beeListResource.message,
                        captchaUrl = beeListResource.captchaUrl
                    )
                }
            }.collect { combinedState ->
                _raceScreenState.value = combinedState
            }
        }
    }
}

sealed class RaceScreenState {
    object Loading : RaceScreenState()
    data class Data(val duration: Int, val bees: List<Bee>) : RaceScreenState()
    data class Error(val message: String?, val captchaUrl: String?) : RaceScreenState()
}