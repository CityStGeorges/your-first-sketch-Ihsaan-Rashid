package com.example.moodbloom.domain.repository

import com.example.moodbloom.domain.models.auth.LoginRequestModel
import com.example.moodbloom.domain.models.auth.RegisterUserRequestModel
import com.example.moodbloom.domain.models.auth.UserModel
import com.example.moodbloom.utils.extension.ResponseStates
import com.google.firebase.auth.FirebaseUser

interface AuthRepo {
     suspend fun signInWithGoogle(idToken: String): ResponseStates<UserModel?>
     suspend fun registerUser(request: RegisterUserRequestModel): ResponseStates<UserModel?>
     suspend fun loginUser(request: LoginRequestModel): ResponseStates<UserModel?>
     suspend fun sendPasswordResetEmail(email: String): ResponseStates<Boolean>
}