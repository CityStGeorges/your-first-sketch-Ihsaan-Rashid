package com.example.moodbloom.presentation.screens.moodtrends

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.moodbloom.domain.models.DateModel
import com.example.moodbloom.presentation.screens.logdailymood.getMoodsList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class MoodsTrendsViewModel @Inject constructor(
) : ViewModel() {

    private val _chartData: MutableStateFlow<List<DateModel>> = MutableStateFlow(listOf())
    val chartData: StateFlow<List<DateModel>> = _chartData.asStateFlow()

    fun getLastDates(last: String) {
        val moodsList = getMoodsList()
        val dateList = mutableListOf<DateModel>()
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val dayFormat = SimpleDateFormat("EEE", Locale.getDefault()) // "Sat"
        val onlyDateFormat = SimpleDateFormat("dd", Locale.getDefault()) // "Sat"
        val monthFormat = SimpleDateFormat("MMM", Locale.getDefault()) // "Dec"
        when {
            last.equals("Daily", ignoreCase = true) -> {
                repeat(7) {
                    val date = calendar.time
                    dateList.add(
                        DateModel(
                            day = dayFormat.format(date),
                            dateFormatted = dateFormat.format(date),
                            month = monthFormat.format(date),
                            date = date,
                            modeType = moodsList.random().type,
                            modeScore = moodsList.random().moodScore,
                            localDate = LocalDate.of(
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH) + 1,
                                calendar.get(Calendar.DAY_OF_MONTH)
                            ), //Create LocalDate from Calendar
                            weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR),
                            label=dayFormat.format(date)
                        )
                    )
                    calendar.add(Calendar.DAY_OF_YEAR, -1) // Move one day back
                }
            }

            last.equals("Weekly", ignoreCase = true) -> {
                repeat(7) {
                    val date = calendar.time
                    dateList.add(
                        DateModel(
                            day = dayFormat.format(date),
                            dateFormatted = dateFormat.format(date),
                            month = monthFormat.format(date),
                            date = date,
                            modeType = moodsList.random().type,
                            modeScore = moodsList.random().moodScore,
                            localDate = LocalDate.of(
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH) + 1,
                                calendar.get(Calendar.DAY_OF_MONTH)
                            ), //Create LocalDate from Calendar
                            weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR),
                            label = "${onlyDateFormat.format(date)} ${monthFormat.format(date)}"
                        )
                    )
                    calendar.add(Calendar.WEEK_OF_YEAR, -1) // Move one week back
                }
            }

            last.equals("Monthly", ignoreCase = true) -> {
                repeat(7) {
                    val date = calendar.time
                    dateList.add(
                        DateModel(
                            day = dayFormat.format(date),
                            dateFormatted = dateFormat.format(date),
                            month = monthFormat.format(date),
                            date = date,
                            modeType = moodsList.random().type,
                            modeScore = moodsList.random().moodScore,
                            localDate = LocalDate.of(
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH) + 1,
                                calendar.get(Calendar.DAY_OF_MONTH)
                            ), //Create LocalDate from Calendar
                            weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR),
                            label = monthFormat.format(date)
                        )
                    )
                    calendar.add(Calendar.MONTH, -1) // Move one month back
                }
            }

            else ->  _chartData.value= emptyList()
        }

         when (last) {
            "Daily" -> {
                _chartData.value=dateList.reversed()
            }

            "Weekly" -> {
                val newDataList: MutableList<DateModel> = mutableListOf()
                val weekFields = WeekFields.of(Locale.getDefault()) // Or specify a locale
                dateList.groupBy { date ->
                    date.localDate.get(weekFields.weekOfWeekBasedYear())
                }.forEach { item ->
                    val avgMood = getAverageMood(item.value)
                    newDataList.add(
                        item.value.first().copy(modeScore = avgMood.first, modeType = avgMood.second)
                    )
                }
                /* val calendar1 = Calendar.getInstance(Locale.getDefault())
                  dateList.groupBy { date ->
                      calendar1.time = date.date
                      calendar1.get(Calendar.WEEK_OF_YEAR)
                 }.forEach {
                     newDataList.add(getAverageMood(it.value))
                  }

                 dateList.groupBy { it.weekOfYear}.forEach {
                     newDataList.add(getAverageMood(it.value))
                 }*/
                _chartData.value=newDataList.reversed()
            }

            "Monthly" -> {
                val newDataList: MutableList<DateModel> = mutableListOf()
                dateList.groupBy { it.month }.forEach { item ->
                    val avgMood = getAverageMood(item.value)
                    newDataList.add(
                        item.value.first()
                            .copy(modeScore = avgMood.first, modeType = avgMood.second)
                    )
                }
                _chartData.value= newDataList.reversed()
            }

            else -> {
                _chartData.value=dateList.reversed()
            }
        }

    }

    private fun getAverageMood(dailyMoods: List<DateModel>): Pair<Int, String> {
        val moodsList =
            getMoodsList() // Assuming getMoodsList() returns List<Mood> where Mood has moodScore and type

        if (dailyMoods.isEmpty()) {
            Pair(4, "Natural")
        }

        val validScores = dailyMoods.map { it.modeScore }

        val averageScore = validScores.average()

        // Find the closest matching mood
        val closestMood = moodsList.minByOrNull { kotlin.math.abs(it.moodScore - averageScore) }

        return if (closestMood != null) {
            Pair(closestMood.moodScore, closestMood.type) // Return moodScore and type
        } else {
            // Handle case when moodsList is empty or no closest mood is found
            Pair(4, "Natural")
        }
    }
}


