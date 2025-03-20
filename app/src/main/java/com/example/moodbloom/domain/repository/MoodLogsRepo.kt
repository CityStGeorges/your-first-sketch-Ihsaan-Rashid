package com.example.moodbloom.domain.repository

import com.example.moodbloom.domain.models.LogMoodsRequest
import com.example.moodbloom.domain.models.LogMoodsResponseModel
import com.example.moodbloom.domain.models.auth.LoginRequestModel
import com.example.moodbloom.domain.models.auth.RegisterUserRequestModel
import com.example.moodbloom.extension.ResponseStates
import com.google.firebase.auth.FirebaseUser

interface MoodLogsRepo {
     suspend fun checkTodayMoodExists(userId: String, date: String):LogMoodsResponseModel?
     suspend fun addOrUpdateMood(request: LogMoodsRequest):ResponseStates<String>
     suspend fun getUserMoodsList(userId: String):ResponseStates<List<LogMoodsResponseModel>>
}