package com.example.stockapp.repositroy

sealed class UiState<out T> {
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val exception: Throwable) : UiState<Nothing>()
    data object Idle : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
    data object Empty : UiState<Nothing>()
}