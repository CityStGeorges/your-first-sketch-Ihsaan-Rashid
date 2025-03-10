package com.example.moodbloom.presentation.screens.logdailymood

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.example.moodbloom.MainViewModel
import com.example.moodbloom.R
import com.example.moodbloom.domain.models.MoodsModel
import com.example.moodbloom.extension.SpacerHeight
import com.example.moodbloom.extension.SpacerWeight
import com.example.moodbloom.presentation.components.PromptTypeShow
import com.example.moodbloom.presentation.components.PromptsViewModel
import com.example.moodbloom.presentation.components.ResourceImage
import com.example.moodbloom.presentation.components.ScreenContainer
import com.example.moodbloom.presentation.components.TextButton
import com.example.moodbloom.presentation.components.TextInputField
import com.example.moodbloom.presentation.components.TopAppBar
import com.example.moodbloom.presentation.components.hpr
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.routes.ScreensRoute
import com.example.moodbloom.ui.typo.HeadlineMediumText

@Composable
fun LogDailyMoodRoute(
    onNavigate: (String) -> Unit,
    onBackClick: () -> Unit,
    mainViewModel: MainViewModel,
    viewModel: LogDailyViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LogDailyMoodScreen(onNavigate = onNavigate, onBackClick = onBackClick)
}

@Composable
internal fun LogDailyMoodScreen(
    promptsViewModel: PromptsViewModel = hiltViewModel(), onNavigate: (String) -> Unit,
    onBackClick: () -> Unit
) {

    val currentPrompt by promptsViewModel.currentPrompt.collectAsStateWithLifecycle()
    var selectedMoodItem:MoodsModel? by remember { mutableStateOf(MoodsModel(title = "Very Happy", anim = R.raw.anim2_mood_very_happy,"VHAPPY")) }
    var aboutMood by remember { mutableStateOf("") }
    ScreenContainer(currentPrompt = currentPrompt) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(title = "Log your Mood today"){
                onBackClick()
            }
            SpacerHeight(5.hpr)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(3.sdp)) {
                itemsIndexed(getMoodsList()){index, item ->
                    Box(modifier = Modifier.weight(1f) , contentAlignment = Alignment.TopEnd) {
                        ItemMoodEmoji(item=item,selectedMoodItem=selectedMoodItem){
                            selectedMoodItem=it
                        }
                    }
                }
            }
            SpacerHeight(2.hpr)
            selectedMoodItem?.let {item->
                Column(modifier = Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
                    ResourceImage(image = LottieCompositionSpec.RawRes(item.anim) , modifier = Modifier.size(100.sdp))
                    HeadlineMediumText(text = item.title)
                }
            }?:run {
                SpacerHeight(120.sdp)
            }
            TextInputField(
                value = aboutMood,
                modifier = Modifier.height(150.sdp),
                onValueChange = { aboutMood = it },
                placeholder = "Write about your mood today",
                label = "About your mood today (Optional)",
                minLines = 5,
                maxLines = 5
            )
            SpacerWeight(1f)
            var goToTrends=false
            TextButton(modifier = Modifier.padding(horizontal = 10.sdp), enabled = selectedMoodItem!=null, text = "Save Mood Entry", onClick = {
                    promptsViewModel.updatePrompt(
                        PromptTypeShow.Confirmation(
                            img = R.drawable.ic_success,
                            title = "Mood entry saved!",
                            message = "Your mood entry has been successfully logged.",
                            positiveButtonText = "View mood trends",
                            positiveButtonClick = {
                                goToTrends= true

                            },
                            negativeButtonText = "Go to home",
                            negativeButtonClick = {

                            },
                            onDismiss = {
                                promptsViewModel.updatePrompt(null)
                                if(goToTrends){
                                    onNavigate(ScreensRoute.MoodTrends.route)
                                }else{
                                    onBackClick()
                                }
                            }
                        )
                    )
                })
            SpacerHeight(5.hpr)
        }
    }
}

fun getMoodsList():List<MoodsModel>{
   return listOf(
        MoodsModel(title = "Very Happy", anim = R.raw.anim2_mood_very_happy,"VHAPPY", moodScore = 6),
        MoodsModel(title = "Happy", anim = R.raw.anim2_mood_happy,"HAPPY", moodScore = 5),
        MoodsModel(title = "Natural", anim = R.raw.anim2_mood_natural,"NATURAL", moodScore = 4),
        MoodsModel(title = "Uncertain", anim = R.raw.anim2_mood_uncertain,"UNCERTAIN", moodScore = 3),
        MoodsModel(title = "Sad", anim = R.raw.anim2_mood_sad,"SAD", moodScore = 2),
        MoodsModel(title = "Very Sad", anim = R.raw.anim2_mood_very_sad,"VSAD", moodScore = 1),
        MoodsModel(title = "Angry", anim = R.raw.anim2_mood_angry,"ANGRY", moodScore = 0),
    )
}