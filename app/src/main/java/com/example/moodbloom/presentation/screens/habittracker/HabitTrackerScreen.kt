package com.example.moodbloom.presentation.screens.habittracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moodbloom.MainViewModel
import com.example.moodbloom.R
import com.example.moodbloom.data.DataHelper.getDummyHabitsList
import com.example.moodbloom.domain.models.HabitTrackerModel
import com.example.moodbloom.extension.SpacerHeight
import com.example.moodbloom.extension.SpacerWeight
import com.example.moodbloom.presentation.components.PromptsViewModel
import com.example.moodbloom.presentation.components.ResourceImage
import com.example.moodbloom.presentation.components.ScreenContainer
import com.example.moodbloom.presentation.components.TopAppBar
import com.example.moodbloom.presentation.components.hpr
import com.example.moodbloom.presentation.components.safeClickable
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.presentation.screens.habittracker.items.ItemHabitProgress
import com.example.moodbloom.routes.ScreensRoute


@Composable
fun HabitTrackerRoute(
    onNavigate: (String) -> Unit,
    onBackClick: () -> Unit,
    mainViewModel: MainViewModel,
    viewModel: HabitTrackerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    HabitTrackerScreen(onNavigate = onNavigate, onBackClick = onBackClick)
}

@Composable
internal fun HabitTrackerScreen(
    promptsViewModel: PromptsViewModel = hiltViewModel(), onNavigate: (String) -> Unit,
    onBackClick: () -> Unit
) {

    val context = LocalContext.current
    val currentPrompt by promptsViewModel.currentPrompt.collectAsStateWithLifecycle()

    var listHabitTracker: List<HabitTrackerModel> by remember { mutableStateOf(getDummyHabitsList()) }
    ScreenContainer(currentPrompt = currentPrompt) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(title = "Your Habits") {
                onBackClick()
            }
            SpacerHeight(5.hpr)
            LazyVerticalGrid(
                horizontalArrangement = Arrangement.spacedBy(2.sdp),
                verticalArrangement = Arrangement.spacedBy(3.sdp),
                columns = GridCells.Fixed(4)
            ) {
                itemsIndexed(listHabitTracker) { index, item ->
                    ItemHabitProgress(
                        modifierContent = Modifier
                            .width(60.sdp)
                            .size(60.sdp),
                        paddingContent = 10.sdp,
                        context = context,
                        item = item
                    ) {
                        if(item.completedPerDay<item.totalPerDay){
                            listHabitTracker[index].completedPerDay++
                        }
                    }
                }
            }
            SpacerWeight(1f)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                ResourceImage(modifier = Modifier
                    .size(40.sdp)
                    .safeClickable {
                        onNavigate(ScreensRoute.SelectHabitTracker.route)
                    }, image = R.drawable.ic_add)
            }
            SpacerHeight(1.hpr)

        }
    }
}


@Preview(showBackground = true)
@Composable
fun HabitScreenPreview() {
    HabitTrackerScreen(onNavigate = {}, onBackClick = {})
}
