package com.example.moodbloom.domain.models

data class HabitTrackerModel(
    val userId:String = "",
    val title:String ="",
    val iconUrl:String ="",
    var selectedDays: List<String> = listOf(),
    var totalPerDay: Int=1,
    var completedPerDay: Int=0,
    var reminderTimes: List<String> = listOf(),
)
