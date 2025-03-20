package com.example.moodbloom.domain.repository

import com.example.moodbloom.domain.models.ConfigurationModel
import com.example.moodbloom.extension.ResponseStates

interface ConfigurationRepo {
     suspend fun checkConfigExists(userId: String):ConfigurationModel?
     suspend fun addOrUpdateConfig(request: ConfigurationModel):ResponseStates<String>
     suspend fun getUserConfig(userId: String):ResponseStates<ConfigurationModel>
     suspend fun requestToDeleteAccount(userId: String):ResponseStates<String>
}