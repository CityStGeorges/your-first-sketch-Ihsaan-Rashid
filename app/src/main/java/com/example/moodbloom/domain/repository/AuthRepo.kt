package com.example.moodbloom.domain.repository

import com.example.moodbloom.domain.models.auth.LoginRequestModel
import com.example.moodbloom.domain.models.auth.RegisterUserRequestModel
import com.example.moodbloom.utils.extension.ResponseStates
import com.google.firebase.auth.FirebaseUser

interface AuthRepo {
     suspend fun signInWithGoogle(idToken: String): ResponseStates<FirebaseUser?>
     suspend fun registerUser(request: RegisterUserRequestModel): ResponseStates<FirebaseUser?>
     suspend fun loginUser(request: LoginRequestModel): ResponseStates<FirebaseUser?>
     suspend fun sendPasswordResetEmail(email: String): ResponseStates<Boolean>
}