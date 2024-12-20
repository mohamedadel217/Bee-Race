package com.example.feature.models

import com.example.domain.models.Bee

sealed class RaceScreenState {
    object Loading : RaceScreenState()
    data class Data(val duration: Int, val bees: List<Bee>) : RaceScreenState()
    data class Error(val message: String?, val captchaUrl: String?) : RaceScreenState()
}