package com.example.moodbloom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


@Composable
fun rememberMyAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): MyAppState {
    return remember(
        navController,
        coroutineScope
    ) {
        MyAppState(
            navController = navController,coroutineScope
        )
    }
}

@Stable
class MyAppState(
    val navController: NavHostController,
    coroutineScope:CoroutineScope,
) {

    private val currentDestination: StateFlow<NavDestination?> =
        navController.currentBackStackEntryFlow.map { it.destination }.stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    fun navigateBack() {
        navController.popBackStack()
    }
}

