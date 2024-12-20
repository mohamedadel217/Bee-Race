package com.example.common

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String, val captchaUrl: String? = null) : Resource<Nothing>()
}