package com.example.data.models

data class RaceStatusResponse(  val beeList: List<BeeResponse>,
                                val captchaUrl: String? = null )
