package com.example.moodbloom.data.repository

import android.content.Context
import android.util.Log
import com.example.moodbloom.R
import com.example.moodbloom.domain.models.auth.LoginRequestModel
import com.example.moodbloom.domain.models.auth.RegisterUserRequestModel
import com.example.moodbloom.domain.repository.AuthRepo
import com.example.moodbloom.utils.extension.ResponseStates
import com.example.moodbloom.utils.extension.isNetworkAvailable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
) : AuthRepo {

    override suspend fun signInWithGoogle(idToken: String): ResponseStates<FirebaseUser?> {
        return try {
            if (context.isNetworkAvailable()) {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                val result = auth.signInWithCredential(credential).await()
                val firebaseUser = result.user
                firebaseUser?.let {
                    val userData = hashMapOf(
                        "uid" to it.uid,
                        "email" to it.email,
                        "fullName" to it.displayName,
                        "username" to it.email?.replace("@gmail.com", ""),
                    )
                    firestore.collection("users").document(it.uid).set(userData).await()
                }
                ResponseStates.Success(200, result.user)
            } else {
                ResponseStates.Failure(
                    111,
                    context.getString(R.string.connected_to_the_internet_and_try_again)
                )
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Google Sign-In Error: ${e.message}")
            ResponseStates.Failure(999, "Google Sign-In Error: ${e.message}")
        }
    }

    override suspend fun registerUser(request: RegisterUserRequestModel): ResponseStates<FirebaseUser?> {
        return try {
            if (context.isNetworkAvailable()) {
                val result =
                    auth.createUserWithEmailAndPassword(request.email, request.password).await()
                val firebaseUser = result.user
                firebaseUser?.let {
                    firestore.collection("users").document(it.uid).set(request.copy(uid = it.uid))
                        .await()
                }
                ResponseStates.Success(200, firebaseUser)
            } else {
                ResponseStates.Failure(
                    111,
                    context.getString(R.string.connected_to_the_internet_and_try_again)
                )
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error: ${e.message}")
            ResponseStates.Failure(999, "Error: ${e.message}")
        }
    }

    override suspend fun loginUser(request: LoginRequestModel): ResponseStates<FirebaseUser?> {
        return try {
            if (context.isNetworkAvailable()) {
                val result =
                    auth.signInWithEmailAndPassword(request.email, request.password).await()
                ResponseStates.Success(200, result.user)
            } else {
                ResponseStates.Failure(
                    111,
                    context.getString(R.string.connected_to_the_internet_and_try_again)
                )
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Login Error: ${e.message}")
            ResponseStates.Failure(999, "Login Error: ${e.message}")
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): ResponseStates<Boolean> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            ResponseStates.Success(200, true)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Password Reset Error: ${e.message}")
            ResponseStates.Failure(999, "Password Reset Error: ${e.message}")
        }
    }

}