package com.example.moodbloom.domain.models.auth

data class LoginRequestModel(
    val email: String,
    val password: String
)