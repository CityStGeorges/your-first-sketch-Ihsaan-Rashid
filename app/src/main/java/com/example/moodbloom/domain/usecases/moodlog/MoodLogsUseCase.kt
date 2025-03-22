package com.example.moodbloom.domain.usecases.moodlog

import com.example.moodbloom.domain.models.LogMoodsRequest
import com.example.moodbloom.domain.models.LogMoodsResponseModel
import com.example.moodbloom.domain.repository.MoodLogsRepo
import com.example.moodbloom.utils.extension.ResponseStates
import javax.inject.Inject

class DailyMoodLogUseCase @Inject constructor(private val repo: MoodLogsRepo) {
    suspend fun invoke(params: LogMoodsRequest): ResponseStates<String> {
        return repo.addOrUpdateMood(params)
    }
}

class GetUserAllMoodLogListUseCase @Inject constructor(private val repo: MoodLogsRepo) {
    suspend fun invoke(userid: String): ResponseStates<List<LogMoodsResponseModel>> {
        return repo.getUserMoodsList(userid)
    }
}

class CheckMoodLoggedTodayUseCase @Inject constructor(private val repo: MoodLogsRepo) {
    suspend fun invoke(userid: String,date: String): LogMoodsResponseModel? {
        return repo.checkTodayMoodExists(userid,date)
    }
}