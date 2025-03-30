package com.example.moodbloom.data.repository

import android.content.Context
import android.util.Log
import com.example.moodbloom.R
import com.example.moodbloom.domain.models.auth.LoginRequestModel
import com.example.moodbloom.domain.models.auth.RegisterUserRequestModel
import com.example.moodbloom.domain.models.auth.UserModel
import com.example.moodbloom.domain.repository.AuthRepo
import com.example.moodbloom.utils.extension.ResponseStates
import com.example.moodbloom.utils.extension.isNetworkAvailable
import com.google.firebase.auth.FirebaseAuth
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
    private val userCollection = firestore.collection("users")
    override suspend fun signInWithGoogle(idToken: String): ResponseStates<UserModel?> {
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
                    ResponseStates.Success(
                        200, UserModel(
                            firebaseUser = result.user,
                            fullName = firebaseUser.displayName
                                ?: firebaseUser.email?.replace("@gmail.com", "") ?: "",
                            username = firebaseUser.email?.replace("@gmail.com", "") ?: "",
                            email = firebaseUser.email ?: "",
                            uid = firebaseUser.uid
                        )
                    )
                } ?: run {
                    ResponseStates.Failure(999, "Google Sign-In Failed")
                }
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

    override suspend fun registerUser(request: RegisterUserRequestModel): ResponseStates<UserModel?> {
        return try {
            if (context.isNetworkAvailable()) {
                val result =
                    auth.createUserWithEmailAndPassword(request.email, request.password).await()
                val firebaseUser = result.user
                firebaseUser?.let {
                    val userData = hashMapOf(
                        "uid" to it.uid,
                        "email" to request.email,
                        "fullName" to request.fullName,
                        "username" to request.username,
                    )
                    firestore.collection("users").document(it.uid).set(userData)
                        .await()
                }
                ResponseStates.Success(
                    200,
                    UserModel(
                        firebaseUser = firebaseUser,
                        fullName = request.fullName,
                        username = request.username,
                        email = request.email,
                        uid = request.uid ?: ""
                    )
                )
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

    override suspend fun loginUser(request: LoginRequestModel): ResponseStates<UserModel?> {
        return try {
            if (context.isNetworkAvailable()) {
                val result =
                    auth.signInWithEmailAndPassword(request.email, request.password).await()
                result.user?.let { user ->
                    val querySnapshot = userCollection
                        .whereEqualTo("uid", user.uid)
                        .get()
                        .await()
                    if (querySnapshot.documents.isNotEmpty()) {
                        Log.d("AuthRepository", "User found ${querySnapshot.documents.size}")
                        querySnapshot.documents.first().toObject(UserModel::class.java)?.let {
                            Log.d("AuthRepository", "User found ${user.uid} && ${it.fullName}")
                            ResponseStates.Success(200, it.copy(firebaseUser = user))
                        } ?: run {
                            ResponseStates.Failure(999, "User not found.")
                        }
                    } else {
                        ResponseStates.Failure(999, "User not found.")
                    }
                } ?: run {
                    ResponseStates.Failure(999, "User not found.")
                }
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