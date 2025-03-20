package com.example.moodbloom.domain.usecases.auth

import com.example.moodbloom.domain.models.auth.LoginRequestModel
import com.example.moodbloom.domain.models.auth.RegisterUserRequestModel
import com.example.moodbloom.domain.repository.AuthRepo
import com.example.moodbloom.extension.ResponseStates
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject


class SignInWithGoogleUseCase @Inject constructor(private val repo: AuthRepo) {
    suspend fun invoke(params: String): ResponseStates<FirebaseUser?> {
        return repo.signInWithGoogle(params)
    }
}

class RegisterUseCase @Inject constructor(private val repo: AuthRepo) {
    suspend fun invoke(params: RegisterUserRequestModel): ResponseStates<FirebaseUser?> {
        return repo.registerUser(params)
    }
}


class LoginUseCase @Inject constructor(private val repo: AuthRepo) {
    suspend fun invoke(params: LoginRequestModel): ResponseStates<FirebaseUser?> {
        return repo.loginUser(params)
    }
}

class SendPasswordResetEmailUseCase @Inject constructor(private val repo: AuthRepo) {
    suspend fun invoke(params: String): ResponseStates<Boolean> {
        return repo.sendPasswordResetEmail(params)
    }
}
class ResetPasswordEmailUseCase @Inject constructor(private val repo: AuthRepo) {
    suspend fun invoke(params: String): ResponseStates<Boolean> {
        return repo.sendPasswordResetEmail(params)
    }
}

