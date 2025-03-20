package com.example.moodbloom.presentation.screens.logdailymood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodbloom.domain.models.LogMoodsRequest
import com.example.moodbloom.domain.usecases.moodlog.DailyMoodLogUseCase
import com.example.moodbloom.extension.ResponseStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LogDailyViewModel @Inject constructor(
    private val dailyMoodLogUseCase: DailyMoodLogUseCase
) : ViewModel() {

    private val sdfDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val sdfMonth = SimpleDateFormat("MMM", Locale.getDefault())
    private val sdfDay = SimpleDateFormat("EEE", Locale.getDefault())



    private val _logMoodState = MutableStateFlow<ResponseStates<String>>(ResponseStates.Idle)
    val logMoodState: StateFlow<ResponseStates<String>> = _logMoodState

    fun logMood(request:LogMoodsRequest) {
        viewModelScope.launch {
            _logMoodState.value= ResponseStates.Loading
            val currentDate = Date()
            _logMoodState.value= dailyMoodLogUseCase.invoke(request.copy(date = sdfDate.format(currentDate),
                month = sdfMonth.format(currentDate),
                day = sdfDay.format(currentDate),
                timeStamp = currentDate.time))
        }
    }


    fun clearLogMoodState(){
        _logMoodState.value= ResponseStates.Idle
    }


}



