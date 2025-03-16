package com.example.moodbloom.presentation.screens.excersice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodbloom.data.DataHelper
import com.example.moodbloom.domain.models.ExerciseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExerciseViewModel @Inject constructor(
) : ViewModel() {

    private val _listExercise: MutableStateFlow<List<ExerciseModel>> =
        MutableStateFlow(listOf())
    val listExercise: StateFlow<List<ExerciseModel>> =
        _listExercise.asStateFlow()


    fun getExerciseList() {
        viewModelScope.launch {
            _listExercise.value = DataHelper.getExerciseList()
        }
    }
}
