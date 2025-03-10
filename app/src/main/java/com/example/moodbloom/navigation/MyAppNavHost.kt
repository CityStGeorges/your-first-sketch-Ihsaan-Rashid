package com.example.moodbloom.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moodbloom.MainViewModel
import com.example.moodbloom.presentation.screens.home.HomeScreenRoute
import com.example.moodbloom.presentation.screens.logdailymood.LogDailyMoodRoute
import com.example.moodbloom.presentation.screens.login.LoginScreenRoute
import com.example.moodbloom.presentation.screens.moodtrends.MoodTrendsScreenRoute
import com.example.moodbloom.presentation.screens.signup.SignUpScreenRoute
import com.example.moodbloom.presentation.screens.splash.SplashRoute
import com.example.moodbloom.presentation.screens.welcome.WelcomeScreenRoute
import com.example.moodbloom.routes.ScreensRoute


@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    mainViewModel: MainViewModel,
    startDestination: String = ScreensRoute.Splash.route
) {
    NavHost(
        navController = navController, startDestination = startDestination, modifier = modifier
    ) {
        composable(route = ScreensRoute.Splash.route) {
            SplashRoute(onSplashCompleted = {
                navController.navigateSplashToOther(it)
            })
        }

        composable(route = ScreensRoute.Welcome.route) {
            WelcomeScreenRoute(mainViewModel=mainViewModel,onNavigate = {
                navController.navigateToScreen(it)
            })
        }
        composable(route = ScreensRoute.Login.route) {
            LoginScreenRoute(mainViewModel=mainViewModel,onNavigate = {
                navController.navigateToScreen(it)
            })
        }
        composable(route = ScreensRoute.SignUp.route) {
            SignUpScreenRoute(mainViewModel=mainViewModel,onNavigate = {
                navController.navigateToScreen(it)
            })
        }
        composable(route = ScreensRoute.Home.route) {
            HomeScreenRoute(mainViewModel=mainViewModel,onNavigate = {
                navController.navigateToScreen(it)
            })
        }
        composable(route = ScreensRoute.LogDailyMood.route) {
            LogDailyMoodRoute(mainViewModel=mainViewModel,onNavigate = {
                navController.navigateToScreen(it)
            }, onBackClick = {
                navController.popBackStack()
            })
        }
        composable(route = ScreensRoute.MoodTrends.route) {
            MoodTrendsScreenRoute(mainViewModel=mainViewModel,onNavigate = {
                navController.navigateToScreen(it)
            }, onBackClick = {
                navController.popBackStack()
            })
        }


    }
}

fun NavController.navigateToScreen(route: String, navOptions: NavOptions? = null) {
    try {
        navigate(route, navOptions)
    } catch (e: IllegalArgumentException) {
        //navigateToPageNotFound(route)
    }
}

fun NavController.navigateSplashToOther(route: String) {
    navigate(route = route) {
        popUpTo(route = ScreensRoute.Splash.route) { inclusive = true }
    }
}