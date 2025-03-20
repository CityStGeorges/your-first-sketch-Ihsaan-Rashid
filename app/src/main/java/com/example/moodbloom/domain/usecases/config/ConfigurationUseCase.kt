package com.example.moodbloom.domain.usecases.config

import com.example.moodbloom.domain.models.ConfigurationModel
import com.example.moodbloom.domain.models.LogMoodsRequest
import com.example.moodbloom.domain.models.LogMoodsResponseModel
import com.example.moodbloom.domain.repository.ConfigurationRepo
import com.example.moodbloom.domain.repository.MoodLogsRepo
import com.example.moodbloom.extension.ResponseStates
import javax.inject.Inject

class AdOrUpdateConfigUseCase @Inject constructor(private val repo: ConfigurationRepo) {
    suspend fun invoke(params: ConfigurationModel): ResponseStates<String> {
        return repo.addOrUpdateConfig(params)
    }
}

class GetUserConfigUseCase @Inject constructor(private val repo: ConfigurationRepo) {
    suspend fun invoke(userid: String): ResponseStates<ConfigurationModel> {
        return repo.getUserConfig(userid)
    }
}

class CheckConfigExistsUseCase @Inject constructor(private val repo: ConfigurationRepo) {
    suspend fun invoke(userid: String): ConfigurationModel? {
        return repo.checkConfigExists(userid)
    }
}
class DeleteAccountUseCase @Inject constructor(private val repo: ConfigurationRepo) {
    suspend fun invoke(userid: String): ResponseStates<String> {
        return repo.requestToDeleteAccount(userid)
    }
}

