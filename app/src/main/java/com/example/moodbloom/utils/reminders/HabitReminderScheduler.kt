package com.example.moodbloom.utils.reminders

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.moodbloom.domain.models.HabitTrackerModel
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.concurrent.TimeUnit

object HabitReminderScheduler {

    fun scheduleHabitReminder(context: Context, habitTitle: String, delayInMinutes: Long) {
        val workManager = WorkManager.getInstance(context)

        val data = workDataOf("title" to habitTitle)

        val reminderRequest = OneTimeWorkRequestBuilder<HabitReminderWorker>()
            .setInitialDelay(delayInMinutes, TimeUnit.MINUTES)
            .setInputData(data)
            .addTag(habitTitle)
            .build()

        workManager.enqueue(reminderRequest)
    }
    fun setHabitReminder(context: Context, habitList: List<HabitTrackerModel>) {
        habitList.forEach { habit->
            habit.reminderTimes.forEach { reminderTime ->
                val delayInMinutes = calculateDelayForSelectedDays(reminderTime, habit.selectedDays)
                if (delayInMinutes != null) {
                    scheduleHabitReminder(context, habit.title, delayInMinutes)
                }
            }
        }
    }


    fun updateHabitReminder(
        context: Context,
        habitTitle: String,
        newReminderTimes: List<String>,
        selectedDays: List<String>
    ) {
        val workManager = WorkManager.getInstance(context)

        // Cancel existing reminders for this habit
        workManager.cancelAllWorkByTag(habitTitle)

        // Schedule new reminders only on selected days
        newReminderTimes.forEach { reminderTime ->
            val delayInMinutes = calculateDelayForSelectedDays(reminderTime, selectedDays)
            if (delayInMinutes != null) {
                scheduleHabitReminder(context, habitTitle, delayInMinutes)
            }
        }
    }
    fun cancelAllHabitReminders(context: Context) {
        WorkManager.getInstance(context).cancelAllWork()
    }
    fun cancelHabitReminder(context: Context, habitTitle: String) {
        val workManager = WorkManager.getInstance(context)
        workManager.cancelAllWorkByTag(habitTitle)
    }
    private fun calculateDelay(reminderTime: String): Long {
        val now = System.currentTimeMillis()
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        val reminderMillis = formatter.parse(reminderTime)?.time ?: now
        return maxOf((reminderMillis - now) / (1000 * 60), 1) // Convert to minutes
    }

    private fun calculateDelayForSelectedDays(reminderTime: String, selectedDays: List<String>): Long? {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val reminderLocalTime = LocalTime.parse(reminderTime, formatter)

        // Convert selected days to Java's DayOfWeek
        val selectedDayEnums = selectedDays.mapNotNull { getDayOfWeek(it) }

        // Get the next available day
        for (i in 0..6) {
            val nextDay = now.plusDays(i.toLong()).dayOfWeek
            if (selectedDayEnums.contains(nextDay)) {
                val reminderDateTime = LocalDateTime.of(now.toLocalDate().plusDays(i.toLong()), reminderLocalTime)
                val delay = Duration.between(now, reminderDateTime).toMinutes()
                return if (delay > 0) delay else delay + 7 * 24 * 60 // Ensure it's a future reminder
            }
        }
        return null
    }

    // Helper function to map selected day strings to Java's DayOfWeek
    private fun getDayOfWeek(day: String): DayOfWeek? {
        return when (day.uppercase(Locale.getDefault())) {
            "MON" -> DayOfWeek.MONDAY
            "TUE" -> DayOfWeek.TUESDAY
            "WED" -> DayOfWeek.WEDNESDAY
            "THR" -> DayOfWeek.THURSDAY
            "FRI" -> DayOfWeek.FRIDAY
            "SAT" -> DayOfWeek.SATURDAY
            "SUN" -> DayOfWeek.SUNDAY
            else -> null
        }
    }
}
