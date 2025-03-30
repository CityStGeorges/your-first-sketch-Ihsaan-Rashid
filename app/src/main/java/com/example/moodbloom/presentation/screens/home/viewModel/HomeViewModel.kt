package com.example.moodbloom.presentation.screens.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodbloom.domain.models.ChartDataModel
import com.example.moodbloom.domain.models.LogMoodsResponseModel
import com.example.moodbloom.domain.models.auth.UserModel
import com.example.moodbloom.domain.usecases.config.DeleteAccountUseCase
import com.example.moodbloom.domain.usecases.moodlog.GetUserAllMoodLogListUseCase
import com.example.moodbloom.presentation.screens.moodtrends.getMoodTypeFromScore
import com.example.moodbloom.utils.extension.MoodType
import com.example.moodbloom.utils.extension.ResponseStates
import com.example.moodbloom.utils.extension.isNotNullOrBlank
import com.example.moodbloom.utils.extension.onFailure
import com.example.moodbloom.utils.extension.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val getUserAllMoodLogListUseCase: GetUserAllMoodLogListUseCase,
) : ViewModel() {


    private val _deleteAccountState =
        MutableStateFlow<ResponseStates<String>>(ResponseStates.Idle)
    val deleteAccountState: StateFlow<ResponseStates<String>> = _deleteAccountState

    fun deleteAccount(userId: String) {
        viewModelScope.launch {
            _deleteAccountState.value = ResponseStates.Loading
            _deleteAccountState.value = deleteAccountUseCase.invoke(userId)
        }
    }


    private val _moodThisWeekState =
        MutableStateFlow<ResponseStates<MoodType>>(ResponseStates.Idle)
    val listLogMoodState: StateFlow<ResponseStates<MoodType>> = _moodThisWeekState
    fun getUserAllMoodLogList(firebaseUser: UserModel?) {
        viewModelScope.launch {
            if (firebaseUser?.uid.isNotNullOrBlank()) {
                _moodThisWeekState.value = ResponseStates.Loading
                    getUserAllMoodLogListUseCase.invoke(firebaseUser?.uid ?: "").onSuccess {
                        if(it.isNotEmpty()){
                            val today = LocalDate.now()
                            var weekList= getMoodsThisWeek(it,today)
                            _moodThisWeekState.value = ResponseStates.Success(200,weekList.map { it.modeScore }.average().toInt().getMoodTypeFromScore())
                        }else{
                            _moodThisWeekState.value = ResponseStates.Success(200,4.getMoodTypeFromScore())
                        }
                    }.onFailure {
                        _moodThisWeekState.value = ResponseStates.Success(200,4.getMoodTypeFromScore())
                    }
            }
        }
    }

    private fun getMoodsThisWeek(
        logMoods: List<LogMoodsResponseModel>, today: LocalDate
    ): List<ChartDataModel> {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dayFormatter = DateTimeFormatter.ofPattern("EEE")
        /* val sdfMonth = SimpleDateFormat("MMM", Locale.getDefault())
         val sdfDay = SimpleDateFormat("EEE", Locale.getDefault())*/
        val last7Days = (0..6).map { today.minusDays(it.toLong()) }.reversed()
        val logMoodMap = logMoods.associateBy { it.date }

        return last7Days.map { date ->
            val logMood = logMoodMap[date.format(dateFormatter)]
            ChartDataModel(
                day = date.dayOfWeek.name,
                dateFormatted = date.format(dateFormatter),
                month = date.month.name,
                localDate = date,
                date = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                modeType = logMood?.type ?: MoodType.NORMAL.type,
                modeScore = logMood?.moodScore ?: MoodType.NORMAL.moodScore,
                label = date.format(dayFormatter),
                weekOfYear = date.get(WeekFields.of(Locale.getDefault()).weekOfYear()),
                aboutMood = logMood?.aboutMood ?: ""
            )
        }
    }
}


