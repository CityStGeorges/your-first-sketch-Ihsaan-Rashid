package com.example.moodbloom.data

import com.example.moodbloom.R
import com.example.moodbloom.domain.models.DaysModel
import com.example.moodbloom.domain.models.ExerciseModel
import com.example.moodbloom.domain.models.HabitTrackerModel

object DataHelper {

    fun getHabitsList(): List<HabitTrackerModel> {
        return listOf(
            HabitTrackerModel(
                title = "Exercise",
                iconUrl = "R.drawable.ic_habit_excercise",
                selectedDays = listOf(),
                reminderTimes = listOf()
            ),

            HabitTrackerModel(
                title = "Hydration",
                iconUrl = "R.drawable.ic_habit_hydration",
                selectedDays = listOf(),
                reminderTimes = listOf()
            ),

            HabitTrackerModel(
                title = "Diet",
                iconUrl = "R.drawable.ic_habit_diet",
                selectedDays = listOf(),
                reminderTimes = listOf()
            ),

            HabitTrackerModel(
                title = "Sleep",
                iconUrl = "R.drawable.ic_habit_sleep",
                selectedDays = listOf(),
                reminderTimes = listOf()
            ),
            HabitTrackerModel(
                title = "Mindfulness",
                iconUrl = "R.drawable.ic_habit_mindfulnes",
                selectedDays = listOf(),
                reminderTimes = listOf()
            ),

            HabitTrackerModel(
                title = "Limit Screen",
                iconUrl = "R.drawable.ic_habit_limit_screen",
                selectedDays = listOf(),
                reminderTimes = listOf()
            ),

            HabitTrackerModel(
                title = "Brush",
                iconUrl = "R.drawable.ic_habit_brush",
                selectedDays = listOf(),
                reminderTimes = listOf()
            ),

            HabitTrackerModel(
                title = "Music",
                iconUrl = "R.drawable.ic_habit_music",
                selectedDays = listOf(),
                reminderTimes = listOf()
            ),


            HabitTrackerModel(
                title = "Jogging",
                iconUrl = "R.drawable.ic_habit_jogging",
                selectedDays = listOf(),
                reminderTimes = listOf()
            ),
            HabitTrackerModel(
                title = "Cycling",
                iconUrl = "R.drawable.ic_habit_cycling",
                selectedDays = listOf(),
                reminderTimes = listOf()
            ),
            HabitTrackerModel(
                title = "Quit\nSmoking",
                iconUrl = "R.drawable.ic_habit_smooking",
                selectedDays = listOf(),
                reminderTimes = listOf()
            ),
            HabitTrackerModel(
                title = "Avoid\nJunk Food",
                iconUrl = "R.drawable.ic_habit_junkfood",
                selectedDays = listOf(),
                reminderTimes = listOf()
            ),
            HabitTrackerModel(
                title = "Volunteering",
                iconUrl = "R.drawable.ic_habit_volunteering",
                selectedDays = listOf(),
                reminderTimes = listOf()
            ),
            HabitTrackerModel(
                title = "Cardio",
                iconUrl = "R.drawable.ic_habit_cardio",
                selectedDays = listOf(),
                reminderTimes = listOf()
            ),
            HabitTrackerModel(
                title = "Set Goals",
                iconUrl = "R.drawable.ic_habit_setgoals",
                selectedDays = listOf(),
                reminderTimes = listOf()
            ),
            HabitTrackerModel(
                title = "Financial\nDiscipline",
                iconUrl = "R.drawable.ic_habit_financial",
                selectedDays = listOf(),
                reminderTimes = listOf()
            ),


            )
    }
    fun getExerciseList(): List<ExerciseModel> {
        return  listOf(
            ExerciseModel(
                title = "Diaphragmatic Breathing (Belly Breathing)",
                icon = R.raw.yoga_breathing,
                guidelines = listOf(
                    "Sit or lie down in a comfortable position.",
                    "Place one hand on your chest and the other on your belly.",
                    "Inhale deeply through your nose, expanding your belly (not your chest).",
                    "Exhale slowly through your mouth.",
                    "Repeat for 5–10 minutes."
                ),
                bestFor = "Anxiety, stress reduction, lung efficiency",
                tips = "Keep your chest still and focus on belly movement."
            ),
            ExerciseModel(
                title = "Box Breathing (Square Breathing)",
                icon = R.raw.yoga_breathing,
                guidelines = listOf(
                    "Inhale through your nose for 4 seconds.",
                    "Hold your breath for 4 seconds.",
                    "Exhale slowly through your mouth for 4 seconds.",
                    "Hold your breath for 4 seconds.",
                    "Repeat the cycle 4–5 times."
                ),
                bestFor = "Stress relief, focus, emotional control",
                tips = "Imagine drawing a square in your mind as you breathe."
            ),
            ExerciseModel(
                title = "4-7-8 Breathing",
                icon = R.raw.yoga_breathing,
                guidelines = listOf(
                    "Inhale deeply through your nose for 4 seconds.",
                    "Hold your breath for 7 seconds.",
                    "Exhale slowly through your mouth for 8 seconds.",
                    "Repeat for 4–8 rounds."
                ),
                bestFor = "Relaxation, sleep improvement, calming the nervous system",
                tips = "Make your exhale longer to activate the parasympathetic nervous system."
            ),
            ExerciseModel(
                title = "Alternate Nostril Breathing (Nadi Shodhana)",
                icon = R.raw.yoga_breathing,
                guidelines = listOf(
                    "Sit in a comfortable position.",
                    "Close your right nostril with your thumb and inhale through the left nostril.",
                    "Close your left nostril with your ring finger, open the right nostril, and exhale.",
                    "Inhale through the right nostril, then switch and exhale through the left nostril.",
                    "Repeat for 5 minutes."
                ),
                bestFor = "Balance, mental clarity, reducing anxiety",
                tips = "Keep your breath steady and gentle, not forceful."
            ),
            ExerciseModel(
                title = "Resonant Breathing (Coherent Breathing)",
                icon = R.raw.yoga_breathing,
                guidelines = listOf(
                    "Breathe in through your nose for 5–6 seconds.",
                    "Exhale through your nose for 5–6 seconds.",
                    "Keep a steady rhythm (about 5–6 breaths per minute).",
                    "Continue for 10 minutes."
                ),
                bestFor = "Lowering blood pressure, heart rate balance",
                tips = "This exercise synchronizes your breath with your heart rate for optimal relaxation."
            ),
            ExerciseModel(
                title = "Wim Hof Breathing",
                icon = R.raw.yoga_breathing,
                guidelines = listOf(
                    "Inhale deeply through your nose or mouth 30 times (rapid but controlled).",
                    "Exhale naturally and hold your breath until you feel the urge to breathe.",
                    "Inhale deeply again and hold for 15 seconds.",
                    "Repeat for 3 rounds."
                ),
                bestFor = "Energy boost, endurance, immune system boost",
                tips = "Don't do this exercise while driving or in water, as it may cause dizziness."
            ),
            ExerciseModel(
                title = "Lion’s Breath (Simhasana)",
                icon = R.raw.yoga_breathing,
                guidelines = listOf(
                    "Sit comfortably and inhale deeply through your nose.",
                    "Open your mouth wide, stick out your tongue, and exhale forcefully with a 'ha' sound.",
                    "Repeat 5–10 times."
                ),
                bestFor = "Releasing tension, increasing confidence",
                tips = "Try this when feeling stressed or overwhelmed."
            ),
            ExerciseModel(
                title = "Pursed-Lip Breathing",
                icon = R.raw.yoga_breathing,
                guidelines = listOf(
                    "Inhale through your nose for 2 seconds.",
                    "Pucker your lips and exhale slowly for 4 seconds.",
                    "Repeat for a few minutes."
                ),
                bestFor = "COPD, asthma, better lung function",
                tips = "This technique slows breathing and improves oxygen exchange."
            )
            )
    }
    fun getDaysList(): List<DaysModel> {
        return listOf(
            DaysModel(title = "MON", day = "Monday", isSelected = false),
            DaysModel(title = "TUE", day = "Tuesday", isSelected = false),
            DaysModel(title = "WED", day = "Wednesday", isSelected = false),
            DaysModel(title = "THR", day = "Thursday", isSelected = false),
            DaysModel(title = "FRI", day = "Friday", isSelected = false),
            DaysModel(title = "SAT", day = "Saturday", isSelected = false),
            DaysModel(title = "SUN", day = "Sunday", isSelected = false),
            )
    }
    fun getDummyHabitsList(): List<HabitTrackerModel> {
        return listOf(
            HabitTrackerModel(
                title = "Exercise",
                iconUrl = "R.drawable.ic_habit_excercise",
                selectedDays = listOf(),
                totalPerDay = 1,
                reminderTimes = listOf()
            ),

            HabitTrackerModel(
                title = "Hydration",
                iconUrl = "R.drawable.ic_habit_hydration",
                selectedDays = listOf(),
                totalPerDay = 1,
                reminderTimes = listOf()
            ),

            HabitTrackerModel(
                title = "Diet",
                iconUrl = "R.drawable.ic_habit_diet",
                selectedDays = listOf(),
                totalPerDay = 10,
                completedPerDay=10,
                reminderTimes = listOf()
            ),

            HabitTrackerModel(
                title = "Sleep",
                iconUrl = "R.drawable.ic_habit_sleep",
                selectedDays = listOf(),
                totalPerDay = 1,
                reminderTimes = listOf()
            ),
            HabitTrackerModel(
                title = "Mindfulness",
                iconUrl = "R.drawable.ic_habit_mindfulnes",
                selectedDays = listOf(),
                totalPerDay = 10,
                completedPerDay=5,
                reminderTimes = listOf()
            ),

            HabitTrackerModel(
                title = "Limit Screen",
                iconUrl = "R.drawable.ic_habit_limit_screen",
                selectedDays = listOf(),
                  totalPerDay = 10,
                completedPerDay=7,
                reminderTimes = listOf()
            ),

            HabitTrackerModel(
                title = "Brush",
                iconUrl = "R.drawable.ic_habit_brush",
                selectedDays = listOf(),
                  totalPerDay = 10,
                completedPerDay=2,
                reminderTimes = listOf()
            ),

            HabitTrackerModel(
                title = "Music",
                iconUrl = "R.drawable.ic_habit_brush",
                selectedDays = listOf(),
                totalPerDay = 1,
                reminderTimes = listOf()
            ),


            HabitTrackerModel(
                title = "Jogging",
                iconUrl = "R.drawable.ic_habit_brush",
                selectedDays = listOf(),
                totalPerDay = 1,
                reminderTimes = listOf()
            ),
            HabitTrackerModel(
                title = "Cycling",
                iconUrl = "R.drawable.ic_habit_brush",
                selectedDays = listOf(),
                totalPerDay = 1,
                reminderTimes = listOf()
            ),
            HabitTrackerModel(
                title = "Quit\nSmoking",
                iconUrl = "R.drawable.ic_habit_brush",
                selectedDays = listOf(),
                totalPerDay = 1,
                reminderTimes = listOf()
            ),
            HabitTrackerModel(
                title = "Avoid\nJunk Food",
                iconUrl = "R.drawable.ic_habit_brush",
                selectedDays = listOf(),
                totalPerDay = 1,
                reminderTimes = listOf()
            ),
            HabitTrackerModel(
                title = "Volunteering",
                iconUrl = "R.drawable.ic_habit_brush",
                selectedDays = listOf(),
                totalPerDay = 1,
                reminderTimes = listOf()
            ),
            HabitTrackerModel(
                title = "Cardio",
                iconUrl = "R.drawable.ic_habit_brush",
                selectedDays = listOf(),
                totalPerDay = 1,
                reminderTimes = listOf()
            ),
            HabitTrackerModel(
                title = "Set Goals",
                iconUrl = "R.drawable.ic_habit_brush",
                selectedDays = listOf(),
                totalPerDay = 1,
                reminderTimes = listOf()
            ),
            HabitTrackerModel(
                title = "Financial\nDiscipline",
                iconUrl = "R.drawable.ic_habit_brush",
                selectedDays = listOf(),
                totalPerDay = 1,
                reminderTimes = listOf()
            ),


            )
    }
}