package com.example.moodbloom.presentation.screens.habittracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodbloom.domain.models.HabitTrackerModel
import com.example.moodbloom.domain.usecases.habit.DeleteHabitUseCase
import com.example.moodbloom.domain.usecases.habit.GetUserAllHabitsListUseCase
import com.example.moodbloom.domain.usecases.habit.InsertHabitUseCase
import com.example.moodbloom.domain.usecases.habit.UpdateHabitUseCase
import com.example.moodbloom.extension.ResponseStates
import com.example.moodbloom.extension.onSuccess
import com.example.moodbloom.reminders.HabitReminderScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitTrackerViewModel @Inject constructor(
    private val insertHabitUseCase: InsertHabitUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val getUserAllHabitsListUseCase: GetUserAllHabitsListUseCase,
) : ViewModel() {

    private val _insertHabitState = MutableStateFlow<ResponseStates<String>>(ResponseStates.Idle)
    val insertHabitState: StateFlow<ResponseStates<String>> = _insertHabitState

    fun insertHabit(request: HabitTrackerModel) {
        viewModelScope.launch {
            _insertHabitState.value= ResponseStates.Loading
            _insertHabitState.value = insertHabitUseCase.invoke(request)
        }
    }

    private val _deleteHabitState = MutableStateFlow<ResponseStates<String>>(ResponseStates.Idle)
    val deleteHabitState: StateFlow<ResponseStates<String>> = _deleteHabitState

    fun deleteHabit(habitTitle: String,userId:String) {
        viewModelScope.launch {
            _deleteHabitState.value= ResponseStates.Loading
            _deleteHabitState.value = deleteHabitUseCase.invoke(habitTitle = habitTitle,userId = userId).onSuccess {
                listHabit(userId)
            }
        }
    }


    private val _updateHabitState = MutableStateFlow<ResponseStates<String>>(ResponseStates.Idle)
    val updateHabitState: StateFlow<ResponseStates<String>> = _updateHabitState

    fun updateHabit(request: HabitTrackerModel) {
        viewModelScope.launch {
            _updateHabitState.value= ResponseStates.Loading
            _updateHabitState.value = updateHabitUseCase.invoke(request)
        }
    }


    private val _listHabitState = MutableStateFlow<ResponseStates<List<HabitTrackerModel>>>(ResponseStates.Idle)
    val listHabitState: StateFlow<ResponseStates<List<HabitTrackerModel>>> = _listHabitState

    fun listHabit(userId: String) {
        viewModelScope.launch {
            _listHabitState.value= ResponseStates.Loading
            _listHabitState.value = getUserAllHabitsListUseCase.invoke(userId)
        }
    }


    fun clearStates(){
        _insertHabitState.value= ResponseStates.Idle
        _deleteHabitState.value= ResponseStates.Idle
        _updateHabitState.value= ResponseStates.Idle
        _listHabitState.value= ResponseStates.Idle
    }



}



