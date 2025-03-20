package com.example.moodbloom.domain.repository

import com.example.moodbloom.domain.models.ChartDataModel
import com.example.moodbloom.domain.models.HabitTrackerModel
import com.example.moodbloom.domain.models.LogMoodsRequest
import com.example.moodbloom.domain.models.LogMoodsResponseModel
import com.example.moodbloom.domain.models.auth.LoginRequestModel
import com.example.moodbloom.domain.models.auth.RegisterUserRequestModel
import com.example.moodbloom.extension.ResponseStates
import com.google.firebase.auth.FirebaseUser

interface AiRepo {
     suspend fun generateInsights(habits: List<HabitTrackerModel>,
                                  moods: List<ChartDataModel>):ResponseStates<String>
     suspend fun generateMoodsInsights(moods: List<ChartDataModel>):ResponseStates<String>
}