package com.example.moodbloom

import androidx.lifecycle.ViewModel
import com.example.moodbloom.domain.models.ExerciseModel
import com.example.moodbloom.domain.models.HabitTrackerModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {


    var selectedHabitTracker :HabitTrackerModel?=null
    var selectedExercise :ExerciseModel?=null

}