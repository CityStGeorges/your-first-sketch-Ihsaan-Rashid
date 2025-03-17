package com.example.moodbloom.domain.models.auth

data class RegisterUserRequestModel(
    val fullName: String,
    val username: String,
    val email: String,
    val password: String,
    val uid: String = ""
)