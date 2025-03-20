package com.example.moodbloom.presentation.screens.logdailymood

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.example.moodbloom.R
import com.example.moodbloom.extension.MoodType
import com.example.moodbloom.presentation.components.ResourceImage
import com.example.moodbloom.presentation.components.safeClickable
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.presentation.components.textSdp
import com.example.moodbloom.ui.typo.BodyMediumText

@Composable
fun BoxScope.ItemMoodEmoji(
    item:  MoodType,
    selectedMoodItem: MoodType?,
    onItemClick: (MoodType) -> Unit
) {
        Column(modifier = Modifier.fillMaxWidth().safeClickable {
            onItemClick(item)
        }, horizontalAlignment = Alignment.CenterHorizontally) {
            ResourceImage(image = LottieCompositionSpec.RawRes(item.emoji) , modifier = Modifier.size(40.sdp))
            BodyMediumText(text = item.title, textAlign = TextAlign.Center, fontSize = 10.textSdp)
        }
        selectedMoodItem?.let {
            if(it.type==item.type){
                ResourceImage(image = R.drawable.ic_success , modifier = Modifier.size(14.sdp))
            }
        }
    }

