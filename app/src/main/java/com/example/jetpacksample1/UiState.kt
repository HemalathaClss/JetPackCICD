package com.example.jetpacksample1

sealed class UiState {
    object Loading : UiState()
    data class Success(val data: List<Post>) : UiState()
    data class Error(val message: String) : UiState()
}


data class Post(
    val id: Int,
    val title: String,
    val body: String
)