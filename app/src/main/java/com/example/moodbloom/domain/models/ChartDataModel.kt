package com.example.moodbloom.domain.models

import java.time.LocalDate
import java.util.Date

data class ChartDataModel(
    val day: String,
    val dateFormatted: String,
    val month: String,
    val localDate: LocalDate,
    val date: Date,
    val modeType: String,
    val modeScore: Int,
    val weekOfYear: Int,
    val label: String ="",
    val aboutMood: String,
)