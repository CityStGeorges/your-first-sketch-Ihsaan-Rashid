package com.example.moodbloom.domain.usecases.openai

import com.example.moodbloom.domain.models.ChartDataModel
import com.example.moodbloom.domain.models.HabitTrackerModel
import com.example.moodbloom.domain.models.LogMoodsResponseModel
import com.example.moodbloom.domain.repository.AiRepo
import com.example.moodbloom.extension.ResponseStates
import javax.inject.Inject

class GenerateInsightsUseCase @Inject constructor(private val repo: AiRepo) {
    suspend fun invoke(userName:String,habits: List<HabitTrackerModel>,
                       moods: List<ChartDataModel>): ResponseStates<String> {
        return repo.generateInsights(userName=userName,habits,moods)
    }
}

class GenerateMoodsInsightsUseCase @Inject constructor(private val repo: AiRepo) {
    suspend fun invoke(userName:String,lastDays:String,moods: List<ChartDataModel>): ResponseStates<String> {
        return repo.generateMoodsInsights(userName=userName,lastDays=lastDays,moods=moods)
    }
}
