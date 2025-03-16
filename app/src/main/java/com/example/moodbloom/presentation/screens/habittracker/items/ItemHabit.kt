package com.example.moodbloom.presentation.screens.habittracker.items

import android.content.Context
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.moodbloom.R
import com.example.moodbloom.domain.models.HabitTrackerModel
import com.example.moodbloom.extension.getDrawableResourceId
import com.example.moodbloom.presentation.components.CardContainer
import com.example.moodbloom.presentation.components.ResourceImage
import com.example.moodbloom.presentation.components.safeClickable
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.ui.theme.LocalShadows
import com.example.moodbloom.ui.theme.Shadow
import com.example.moodbloom.ui.typo.LabelSmallText
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun ItemHabit(
    context: Context,
    modifier: Modifier = Modifier,
    modifierContent: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    shape: Shape = MaterialTheme.shapes.medium,
    shadow: Shadow = LocalShadows.current.light,
    item: HabitTrackerModel,
    onClick: () -> Unit = {}
) {

    Box(contentAlignment = Alignment.TopStart) {
        Column(
            modifier = modifier
                .wrapContentSize()
                .safeClickable { onClick() }, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardContainer(
                containerColor = containerColor,
                borderColor = borderColor,
                shape = shape,
                shadow = shadow
            ) {
                ResourceImage(
                    image = item.iconUrl.getDrawableResourceId(context)
                        ?: R.drawable.ic_habit_custom, modifier = modifierContent
                )
            }
            Spacer(modifier = Modifier.height(5.sdp))
            LabelSmallText(
                text = item.title,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(7.sdp))
        }
    }

}


@Composable
fun ItemHabitProgress(
    context: Context,
    modifier: Modifier = Modifier,
    modifierContent: Modifier = Modifier,
    paddingContent: Dp = 10.sdp,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    shape: Shape = MaterialTheme.shapes.medium,
    shadow: Shadow = LocalShadows.current.light,
    item: HabitTrackerModel,
    onComplete: () -> Unit = {}
) {
    var progress by remember { mutableStateOf(item.completedPerDay.toFloat() / item.totalPerDay.toFloat()) }
    // var progress2 by remember { mutableStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = progress * 60,
        animationSpec = tween(300)
    )

    val coroutineScope = rememberCoroutineScope()
    var job: Job? by remember { mutableStateOf(null) }

    Column(
        modifier = modifier
            .wrapContentSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { offset ->
                        if (item.completedPerDay<item.totalPerDay) {
                            progress=0f
                            job = coroutineScope.launch {
                                while (progress < 1f) {
                                    progress += 0.04f // Increase progress
                                    if (progress >= 1f) {
                                        progress = 1f
                                        onComplete()
                                        break
                                    }
                                    delay(30) // Control progress speed
                                }
                            }
                            tryAwaitRelease() // Wait until user lifts finger
                            job?.cancel() // Cancel progress on release
                            progress =
                                item.completedPerDay.toFloat() / item.totalPerDay.toFloat() // Reset progress
                        }
                    }
                )
            }, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CardContainer(
            containerColor = containerColor,
            borderColor = borderColor,
            shape = shape,
            shadow = shadow
        ) {
            Box {
                ResourceImage(
                    image = item.iconUrl.getDrawableResourceId(context)
                        ?: R.drawable.ic_habit_custom,
                    modifier = modifierContent.padding(paddingContent)
                )
                Box(
                    modifier = Modifier
                        .size(60.sdp, animatedProgress.toInt().sdp)
                        .background(color = MaterialTheme.colorScheme.primary.copy(0.5f))
                        .align(alignment = Alignment.BottomCenter)
                ) { }
                if (item.completedPerDay>=item.totalPerDay) {
                    ResourceImage(
                        image = R.drawable.ic_success,
                        modifier = Modifier
                            .size(23.sdp)
                            .padding(2.sdp)
                            .align(alignment = Alignment.TopStart)
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(5.sdp))
        LabelSmallText(
            text = item.title,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(7.sdp))
    }


}

@Preview(showBackground = true)
@Composable
fun ItemHabitPreview() {
    val context = LocalContext.current
    ItemHabit(
        context = context,
        item = HabitTrackerModel(
            title = "Exercise",
            iconUrl = "R.drawable.ic_habit_excercise",
            selectedDays = listOf(),
            totalPerDay = 1,
            reminderTimes = listOf(),
        ),
    )
}
