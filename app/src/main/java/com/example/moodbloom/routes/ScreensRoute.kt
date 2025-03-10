package com.example.moodbloom.routes


sealed class ScreensRoute (val route: String){

    data object Splash : ScreensRoute(route = "splashScreen")
    data object Welcome : ScreensRoute(route = "welcomeScreen")
    data object Login : ScreensRoute(route = "loginScreen")
    data object SignUp : ScreensRoute(route = "signUpScreen")
    data object Home : ScreensRoute(route = "homeScreen")
    data object LogDailyMood : ScreensRoute(route = "LogDailyMood")
    data object MoodTrends : ScreensRoute(route = "MoodTrends")
    data object HabitTracker : ScreensRoute(route = "HabitTracker")
    data object BreathingExercise : ScreensRoute(route = "BreathingExercise")
    data object Insights : ScreensRoute(route = "Insights")

}