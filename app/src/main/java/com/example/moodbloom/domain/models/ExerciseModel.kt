package com.example.moodbloom.domain.models

data class ExerciseModel(
    val title:String,
    val icon:Int,
    var timerMinutes:Int = 3,
    var voiceGuidance:Boolean = true,
    var relaxingSound:Boolean = true,
    var guidelines: List<String>,
    var bestFor:String,
    var tips:String,
)
