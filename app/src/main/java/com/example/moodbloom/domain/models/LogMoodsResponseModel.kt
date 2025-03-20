package com.example.moodbloom.domain.models

data class LogMoodsResponseModel(
    val userId: String = "",
    val title: String = "",
    val aboutMood: String = "",
    val timeStamp: Long = 0L,
    val date: String = "",
    val month: String = "",
    val day: String = "",
    val type: String = "",
    val moodScore: Int = 0
)

