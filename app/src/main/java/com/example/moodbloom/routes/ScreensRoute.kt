package com.example.moodbloom.routes


sealed class ScreensRoute (val route: String){

    data object Splash : ScreensRoute(route = "splashScreen")
    data object Welcome : ScreensRoute(route = "welcomeScreen")
    data object Login : ScreensRoute(route = "loginScreen")

}