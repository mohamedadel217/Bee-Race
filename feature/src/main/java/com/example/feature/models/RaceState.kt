package com.example.feature.models

import com.example.domain.models.Bee

sealed class RaceState {
    object Loading : RaceState()
    data class Success(val data: List<Bee>) : RaceState()
    data class CaptchaRequired(val url: String) : RaceState()
    data class Error(val message: String) : RaceState()
}