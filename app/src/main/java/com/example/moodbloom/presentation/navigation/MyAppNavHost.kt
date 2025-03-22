package com.example.moodbloom.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moodbloom.presentation.MainViewModel
import com.example.moodbloom.presentation.screens.insights.InsightsScreenRoute
import com.example.moodbloom.presentation.screens.excersice.ExerciseRoute
import com.example.moodbloom.presentation.screens.excersice.SelectExerciseRoute
import com.example.moodbloom.presentation.screens.forget.ForgetPasswordScreenRoute
import com.example.moodbloom.presentation.screens.habittracker.CustomHabitTrackerRoute
import com.example.moodbloom.presentation.screens.habittracker.HabitTrackerRoute
import com.example.moodbloom.presentation.screens.habittracker.SelectHabitTrackerRoute
import com.example.moodbloom.presentation.screens.habittracker.SetUpHabitTrackerRoute
import com.example.moodbloom.presentation.screens.habittracker.UpdateHabitTrackerRoute
import com.example.moodbloom.presentation.screens.home.HomeScreenRoute
import com.example.moodbloom.presentation.screens.logdailymood.LogDailyMoodRoute
import com.example.moodbloom.presentation.screens.login.LoginScreenRoute
import com.example.moodbloom.presentation.screens.moodtrends.MoodTrendsScreenRoute
import com.example.moodbloom.presentation.screens.signup.SignUpScreenRoute
import com.example.moodbloom.presentation.screens.splash.SplashRoute
import com.example.moodbloom.presentation.screens.welcome.WelcomeScreenRoute
import com.example.moodbloom.presentation.routes.ScreensRoute


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
            WelcomeScreenRoute(mainViewModel = mainViewModel, onNavigate = {
                navController.navigateToScreen(it)
            })
        }
        composable(route = ScreensRoute.ForgetPassword.route) {
            ForgetPasswordScreenRoute(mainViewModel = mainViewModel, onNavigate = {
                navController.navigateToScreen(it)
            })
        }
        composable(route = ScreensRoute.Login.route) {
            LoginScreenRoute(mainViewModel = mainViewModel, onNavigate = {
                navController.navigateToScreen(it)
            })
        }
        composable(route = ScreensRoute.SignUp.route) {
            SignUpScreenRoute(mainViewModel = mainViewModel, onNavigate = {
                navController.navigateToScreen(it)
            })
        }
        composable(route = ScreensRoute.Home.route) {
            HomeScreenRoute(mainViewModel = mainViewModel, onNavigate = {
                navController.navigateToScreen(it)
            })
        }
        composable(route = ScreensRoute.LogDailyMood.route) {
            LogDailyMoodRoute(mainViewModel = mainViewModel, onNavigate = {
                navController.navigateToScreen(it)
            }, onBackClick = {
                navController.popBackStack()
            })
        }
        composable(route = ScreensRoute.MoodTrends.route) {
            MoodTrendsScreenRoute(mainViewModel = mainViewModel, onNavigate = {
                navController.navigateToScreen(it)
            }, onBackClick = {
                navController.popBackStack()
            })
        }
        composable(route = ScreensRoute.HabitTracker.route) {
            HabitTrackerRoute(mainViewModel = mainViewModel, onNavigate = {
                navController.navigateToScreen(it)
            }, onBackClick = {
                navController.popBackStack()
            })
        }
        composable(route = ScreensRoute.SelectHabitTracker.route) {
            SelectHabitTrackerRoute(mainViewModel = mainViewModel, onNavigate = {
                navController.navigateToScreen(it)
            }, onBackClick = {
                navController.popBackStack()
            })
        }

        composable(route = ScreensRoute.CustomHabitTracker.route) {
            CustomHabitTrackerRoute(mainViewModel = mainViewModel, onNavigate = {
               navController.navigateWithPopUpTo(ScreensRoute.HabitTracker.route, ScreensRoute.CustomHabitTracker.route)
            }, onBackClick = {
                navController.popBackStack()
            })
        }
        composable(route = ScreensRoute.SetUpHabitTracker.route) {
            SetUpHabitTrackerRoute(mainViewModel = mainViewModel, onNavigate = {
                navController.navigateWithPopUpTo(ScreensRoute.HabitTracker.route, ScreensRoute.SetUpHabitTracker.route)
            }, onBackClick = {
                navController.popBackStack()
            })
        }
        composable(route = ScreensRoute.EditHabitTracker.route) {
            UpdateHabitTrackerRoute(mainViewModel = mainViewModel, onNavigate = {
                navController.navigateWithPopUpTo(ScreensRoute.HabitTracker.route, ScreensRoute.EditHabitTracker.route)
            }, onBackClick = {
                navController.popBackStack()
            })
        }

        composable(route = ScreensRoute.BreathingExercise.route) {
            SelectExerciseRoute(mainViewModel = mainViewModel, onNavigate = {
                navController.navigateToScreen(it)
            }, onBackClick = {
                navController.popBackStack()
            })
        }

        composable(route = ScreensRoute.Exercise.route) {
            ExerciseRoute(mainViewModel = mainViewModel, onNavigate = {
                navController.popBackStack()
            }, onBackClick = {
                navController.popBackStack()
            })
        }
        composable(route = ScreensRoute.Insights.route) {
            InsightsScreenRoute(mainViewModel = mainViewModel, onNavigate = {
                navController.popBackStack()
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

fun NavController.navigateWithPopUpTo(route:String, popUpToRoute:String, isInclusive:Boolean = true) {
    navigate(route = route) {
        popUpTo(route = popUpToRoute) { inclusive = isInclusive }
    }
}