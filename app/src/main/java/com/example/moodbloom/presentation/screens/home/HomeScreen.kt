package com.example.moodbloom.presentation.screens.home

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.example.moodbloom.MainViewModel
import com.example.moodbloom.R
import com.example.moodbloom.domain.models.HomeOptionsModel
import com.example.moodbloom.extension.SpacerHeight
import com.example.moodbloom.extension.SpacerWeight
import com.example.moodbloom.extension.SpacerWidth
import com.example.moodbloom.extension.showToast
import com.example.moodbloom.presentation.components.LogoutButton
import com.example.moodbloom.presentation.components.PromptTypeShow
import com.example.moodbloom.presentation.components.PromptsViewModel
import com.example.moodbloom.presentation.components.ResourceImage
import com.example.moodbloom.presentation.components.ScreenContainer
import com.example.moodbloom.presentation.components.hpr
import com.example.moodbloom.presentation.components.safeClickable
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.presentation.components.textSdp
import com.example.moodbloom.routes.ScreensRoute
import com.example.moodbloom.ui.typo.HeadlineMediumText


@Composable
fun HomeScreenRoute(
    onNavigate: (String) -> Unit,
    mainViewModel: MainViewModel,
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeScreen(onNavigate = onNavigate)

}

@Composable
internal fun HomeScreen(
    promptsViewModel: PromptsViewModel = hiltViewModel(), onNavigate: (String) -> Unit
) {

    val currentPrompt by promptsViewModel.currentPrompt.collectAsStateWithLifecycle()
    var isNotificationEnable by remember { mutableStateOf(false) }
    val listHomeOptions = getHomeOptions()
    val context = LocalContext.current
    fun logout() {
        promptsViewModel.updatePrompt(
            PromptTypeShow.Confirmation(
                img = R.drawable.ic_error,
                title = "Sign Out!",
                message = "Are you sure you want to sign out?",
                positiveButtonText = "No",
                positiveButtonClick = {
                },
                negativeButtonText = "Yes",
                negativeButtonClick = {
                    onNavigate(ScreensRoute.Login.route)
                },
                onDismiss = {
                    promptsViewModel.updatePrompt(null)
                }
            )
        )
    }
    BackHandler {
        logout()
    }
    ScreenContainer(currentPrompt = currentPrompt) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpacerHeight(3.hpr)
            ResourceImage(image = R.drawable.ic_logo, modifier = Modifier.height(12.hpr))
            SpacerHeight(5.hpr)
            HeadlineMediumText(text = "Welcome Ihsaan!", fontSize = 48.textSdp)
            SpacerHeight(3.hpr)
            Row(verticalAlignment = Alignment.CenterVertically) {
                HeadlineMediumText(text = "Your Mood this week:")
                SpacerWidth(5.sdp)
                ResourceImage(
                    image = LottieCompositionSpec.RawRes(R.raw.anim2_mood_very_happy),
                    modifier = Modifier.size(60.sdp)
                )
            }

            SpacerHeight(5.hpr)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(7.sdp)) {
                itemsIndexed(listHomeOptions) { index, item ->
                    if (item.route=="notificationEnable") {
                        ItemOptionNotification(
                            item = item,
                            isChecked = isNotificationEnable,
                            onCheckedChange = {
                                if (it != isNotificationEnable) {
                                    isNotificationEnable = it
                                }
                            })
                    }
                    else {
                        ItemOptions(
                            item = item,
                            modifier = Modifier.safeClickable(rippleEnabled = true) {
                                if (item.route.isNotBlank()) {
                                      if (item.route=="helpline") {
                                        val uri = Uri.parse("tel:" +"123456789")
                                        val intent = Intent(Intent.ACTION_DIAL, uri)
                                        try {
                                            context.startActivity(intent)
                                        } catch (e: SecurityException) {
                                            e.printStackTrace()
                                        }
                                    }else  if (item.route=="deleteAccount") {
                                        promptsViewModel.updatePrompt(
                                            PromptTypeShow.Confirmation(
                                                img = R.drawable.ic_error,
                                                title = "Delete Account!",
                                                message = "Are you sure you want to delete your account?",
                                                positiveButtonText = "No",
                                                positiveButtonClick = {
                                                },
                                                negativeButtonText = "Yes",
                                                negativeButtonClick = {
                                                    context.showToast("Your request for delete account submitted.")
                                                },
                                                onDismiss = {
                                                    promptsViewModel.updatePrompt(null)
                                                }
                                            )
                                        )
                                    }else{
                                        onNavigate(item.route)
                                    }

                                }
                            })
                    }
                }
            }
            SpacerWeight(1f)
            LogoutButton(
                modifier = Modifier.padding(horizontal = 10.sdp),
                onClick = {
                    logout()
                })

            SpacerHeight(5.hpr)
        }
    }
}

fun getHomeOptions(): List<HomeOptionsModel> {
    return listOf(
        HomeOptionsModel(
            title = "Log daily Mood",
            icon = R.drawable.ic_daily_mood,
            route = ScreensRoute.LogDailyMood.route
        ),
        HomeOptionsModel(
            title = "Habit tracker",
            icon = R.drawable.ic_habit_tracker,
            route = ScreensRoute.HabitTracker.route
        ),
        HomeOptionsModel(
            title = "Guided breathing exercises",
            icon = R.drawable.ic_breathing_excercise,
            route = ScreensRoute.BreathingExercise.route
        ),
        HomeOptionsModel(
            title = "Insights",
            icon = R.drawable.ic_insights,
            route = ScreensRoute.Insights.route
        ),
        HomeOptionsModel(title = "Turn on notification", icon = R.drawable.ic_bell, route = "notificationEnable"),
        HomeOptionsModel(
            title = "Request to delete account",
            icon = R.drawable.ic_delete_round,
            route = "deleteAccount"
        ),
        HomeOptionsModel(
            title = "Emergency Helpline",
            icon = R.drawable.ic_helpline,
            route = "helpline"
        ),
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen {}
}