package com.example.moodbloom.routes


sealed class ScreensRoute (val route: String){

    data object Splash : ScreensRoute(route = "splashScreen")
    data object Welcome : ScreensRoute(route = "welcomeScreen")
    data object ForgetPassword : ScreensRoute(route = "forgetPassword")
    data object Login : ScreensRoute(route = "loginScreen")
    data object SignUp : ScreensRoute(route = "signUpScreen")
    data object Home : ScreensRoute(route = "homeScreen")
    data object LogDailyMood : ScreensRoute(route = "LogDailyMood")
    data object MoodTrends : ScreensRoute(route = "MoodTrends")
    data object HabitTracker : ScreensRoute(route = "HabitTracker")
    data object SelectHabitTracker : ScreensRoute(route = "SelectHabitTracker")
    data object CustomHabitTracker : ScreensRoute(route = "CustomHabitTracker")
    data object SetUpHabitTracker : ScreensRoute(route = "SetUpHabitTracker")
    data object EditHabitTracker : ScreensRoute(route = "EditHabitTracker")
    data object BreathingExercise : ScreensRoute(route = "BreathingExercise")
    data object Exercise : ScreensRoute(route = "Exercise")
    data object Insights : ScreensRoute(route = "Insights")

}