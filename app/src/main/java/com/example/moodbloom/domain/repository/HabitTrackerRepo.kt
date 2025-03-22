package com.example.moodbloom.domain.repository

import com.example.moodbloom.domain.models.HabitTrackerModel
import com.example.moodbloom.domain.models.LogMoodsRequest
import com.example.moodbloom.domain.models.LogMoodsResponseModel
import com.example.moodbloom.domain.models.auth.LoginRequestModel
import com.example.moodbloom.domain.models.auth.RegisterUserRequestModel
import com.example.moodbloom.utils.extension.ResponseStates
import com.google.firebase.auth.FirebaseUser

interface HabitTrackerRepo {
     suspend fun insertHabit(request: HabitTrackerModel): ResponseStates<String>
     suspend fun updateHabit(request: HabitTrackerModel): ResponseStates<String>
     suspend fun deleteHabit(habitTitle: String,userId: String): ResponseStates<String>
     suspend fun getAllHabits(userId: String): ResponseStates<List<HabitTrackerModel>>
}