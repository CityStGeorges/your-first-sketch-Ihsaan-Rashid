package com.example.moodbloom.presentation.screens.moodtrends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodbloom.domain.models.ChartDataModel
import com.example.moodbloom.domain.models.LogMoodsResponseModel
import com.example.moodbloom.domain.usecases.moodlog.GetUserAllMoodLogListUseCase
import com.example.moodbloom.domain.usecases.openai.GenerateInsightsUseCase
import com.example.moodbloom.domain.usecases.openai.GenerateMoodsInsightsUseCase
import com.example.moodbloom.extension.ChartType
import com.example.moodbloom.extension.MoodType
import com.example.moodbloom.extension.ResponseStates
import com.example.moodbloom.extension.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class MoodsTrendsViewModel @Inject constructor(
    private val getUserAllMoodLogListUseCase: GetUserAllMoodLogListUseCase,
    private val generateMoodsInsightsUseCase: GenerateMoodsInsightsUseCase,
    private val generateInsightsUseCase: GenerateInsightsUseCase,
) : ViewModel() {

    private val _chartData: MutableStateFlow<List<ChartDataModel>> = MutableStateFlow(listOf())
    val chartData: StateFlow<List<ChartDataModel>> = _chartData.asStateFlow()

    private val _listLogMoodState =
        MutableStateFlow<ResponseStates<List<LogMoodsResponseModel>>>(ResponseStates.Idle)
    val listLogMoodState: StateFlow<ResponseStates<List<LogMoodsResponseModel>>> = _listLogMoodState
    private var listMoodComplete: List<LogMoodsResponseModel> = listOf()

    fun getUserAllMoodLogList(userId: String) {
        viewModelScope.launch {
            _listLogMoodState.value = ResponseStates.Loading
            _listLogMoodState.value = getUserAllMoodLogListUseCase.invoke(userId).onSuccess {
                listMoodComplete = it
               getChartData(chartType = ChartType.DAILY)
            }
        }
    }


    fun getChartData(chartType: ChartType) {
        val today = LocalDate.now()
        _chartData.value =  when (chartType) {
            ChartType.DAILY -> getDailyData(listMoodComplete, today)
            ChartType.WEEKLY -> getWeeklyData(listMoodComplete, today)
            ChartType.MONTHLY -> getMonthlyData(listMoodComplete, today)
        }
        generateMoodsInsights()
    }

    private fun getDailyData(
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
                modeType = logMood?.type ?: MoodType.NATURAL.type,
                modeScore = logMood?.moodScore ?: MoodType.NATURAL.moodScore,
                label = date.format(dayFormatter),
                weekOfYear = date.get(WeekFields.of(Locale.getDefault()).weekOfYear())
            )
        }
    }

    private fun getWeeklyData(
        logMoods: List<LogMoodsResponseModel>, today: LocalDate
    ): List<ChartDataModel> {
        val weekFormatter = DateTimeFormatter.ofPattern("YYYY-ww")
        val last7Weeks = (0..6).map { today.minusWeeks(it.toLong()) }.reversed()
        val groupedByWeek = logMoods.groupBy { LocalDate.parse(it.date).format(weekFormatter) }

        return last7Weeks.map { weekDate ->
            val weekKey = weekDate.format(weekFormatter)
            val moods = groupedByWeek[weekKey] ?: emptyList()
            val avgMoodScore = if (moods.isNotEmpty()) moods.map { it.moodScore }.average()
                .toInt() else MoodType.NATURAL.moodScore

            ChartDataModel(
                day = "Week ${weekDate.get(WeekFields.of(Locale.getDefault()).weekOfYear())}",
                dateFormatted = weekKey,
                month = weekDate.month.name,
                localDate = weekDate,
                date = Date.from(weekDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                modeType = avgMoodScore.getMoodTypeFromScore().type,
                modeScore = avgMoodScore,
                label = "W-${weekDate.get(WeekFields.of(Locale.getDefault()).weekOfYear())}",
                weekOfYear = weekDate.get(WeekFields.of(Locale.getDefault()).weekOfYear())
            )
        }
    }

    private fun getMonthlyData(
        logMoods: List<LogMoodsResponseModel>, today: LocalDate
    ): List<ChartDataModel> {
        val monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM")
        val last7Months = (0..6).map { today.minusMonths(it.toLong()) }.reversed()
        val groupedByMonth = logMoods.groupBy { LocalDate.parse(it.date).format(monthFormatter) }
        val formatMonth = DateTimeFormatter.ofPattern("MMM")
        return last7Months.map { monthDate ->
            val monthKey = monthDate.format(monthFormatter)
            val moods = groupedByMonth[monthKey] ?: emptyList()
            val avgMoodScore = if (moods.isNotEmpty()) moods.map { it.moodScore }.average()
                .toInt() else MoodType.NATURAL.moodScore

            ChartDataModel(
                day = monthDate.month.name,
                dateFormatted = monthKey,
                month = monthDate.month.name,
                localDate = monthDate,
                date = Date.from(monthDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                modeType = avgMoodScore.getMoodTypeFromScore().type,
                modeScore = avgMoodScore,
                label = monthDate.format(formatMonth),
                weekOfYear = monthDate.get(WeekFields.of(Locale.getDefault()).weekOfYear())
            )
        }
    }


    fun Int.getMoodTypeFromScore(): MoodType {
        return when (this) {
            6 -> MoodType.VHAPPY
            5 -> MoodType.HAPPY
            4 -> MoodType.NATURAL
            3 -> MoodType.UNCERTAIN
            2 -> MoodType.SAD
            1 -> MoodType.VSAD
            0 -> MoodType.ANGRY
            else -> MoodType.NATURAL
        }
    }




    private val _generateMoodsInsightsState =
        MutableStateFlow<ResponseStates<String>>(ResponseStates.Idle)
    val generateMoodsInsightsState: StateFlow<ResponseStates<String>> = _generateMoodsInsightsState

    fun generateMoodsInsights() {
        viewModelScope.launch {
            if (chartData.value.isNotEmpty()){
                _generateMoodsInsightsState.value = ResponseStates.Loading
                _generateMoodsInsightsState.value = generateMoodsInsightsUseCase.invoke(chartData.value)
            }
        }
    }

    fun clearLogMoodState() {
        _listLogMoodState.value = ResponseStates.Idle
        _generateMoodsInsightsState.value = ResponseStates.Idle
    }

}


