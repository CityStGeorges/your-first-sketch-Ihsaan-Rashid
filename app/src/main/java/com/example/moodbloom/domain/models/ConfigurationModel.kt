package com.example.moodbloom.domain.models

data class ConfigurationModel(
    val userId:String="",
    val enableNotification:Boolean=true,
    val enableRelaxingSound:Boolean=true,
    val enableVoiceGuidance:Boolean=true,
)
