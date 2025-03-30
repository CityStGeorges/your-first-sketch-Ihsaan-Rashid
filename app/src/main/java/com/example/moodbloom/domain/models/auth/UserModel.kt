package com.example.moodbloom.domain.models.auth

import com.google.firebase.auth.FirebaseUser

data class UserModel(
    val firebaseUser: FirebaseUser? = null,
    val fullName: String = "",
    val username: String = "",
    val email: String = "",
    var uid: String = ""
)