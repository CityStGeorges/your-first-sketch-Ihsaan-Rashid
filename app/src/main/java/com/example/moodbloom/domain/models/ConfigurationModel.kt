package com.example.moodbloom.domain.models

data class ConfigurationModel(
    val userId:String="",
    val isEnableNotification:Boolean=true,
    val isEnableRelaxingSound:Boolean=true,
    val isEnableVoiceGuidance:Boolean=true,
)
