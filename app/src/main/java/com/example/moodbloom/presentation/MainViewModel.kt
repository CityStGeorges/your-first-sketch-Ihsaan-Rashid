package com.example.moodbloom.presentation

import androidx.lifecycle.ViewModel
import com.example.moodbloom.domain.models.ConfigurationModel
import com.example.moodbloom.domain.models.ExerciseModel
import com.example.moodbloom.domain.models.HabitTrackerModel
import com.example.moodbloom.domain.models.auth.RegisterUserRequestModel
import com.example.moodbloom.domain.models.auth.UserModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {


    var selectedHabitTracker :HabitTrackerModel?=null
    var selectedExercise :ExerciseModel?=null

    var firebaseUser:FirebaseUser? = null
    var userModel: UserModel? = null
    var configurationModel: ConfigurationModel = ConfigurationModel()


    fun logout(){
        userModel=null
        firebaseUser=null
        selectedHabitTracker=null
        selectedExercise=null
    }

    fun clearState(){
        selectedHabitTracker=null
        selectedExercise=null
    }



}