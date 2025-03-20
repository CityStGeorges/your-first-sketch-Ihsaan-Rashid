package com.example.moodbloom.presentation.screens.habittracker

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.moodbloom.domain.models.HabitTrackerModel
import com.example.moodbloom.extension.ResponseStates
import com.example.moodbloom.extension.SpacerHeight
import com.example.moodbloom.extension.SpacerWeight
import com.example.moodbloom.extension.showToast
import com.example.moodbloom.presentation.components.HandleApiStates
import com.example.moodbloom.presentation.components.PromptsViewModel
import com.example.moodbloom.presentation.components.ResourceImage
import com.example.moodbloom.presentation.components.ScreenContainer
import com.example.moodbloom.presentation.components.TopAppBar
import com.example.moodbloom.presentation.components.hpr
import com.example.moodbloom.presentation.components.safeClickable
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.presentation.screens.habittracker.items.ItemHabitProgress
import com.example.moodbloom.reminders.HabitReminderScheduler
import com.example.moodbloom.routes.ScreensRoute
import com.example.moodbloom.ui.typo.TitleSmallText


@Composable
fun HabitTrackerRoute(
    onNavigate: (String) -> Unit,
    onBackClick: () -> Unit,
    mainViewModel: MainViewModel,
    viewModel: HabitTrackerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val listHabitState by viewModel.listHabitState.collectAsStateWithLifecycle()
    val updateHabitState by viewModel.updateHabitState.collectAsStateWithLifecycle()
    val deleteHabitState by viewModel.deleteHabitState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.listHabit(mainViewModel.firebaseUser?.uid?:"")
    }
    HabitTrackerScreen(onNavigate = onNavigate,
        onDeleteClick={
            viewModel.deleteHabit(habitTitle = it, userId = mainViewModel.firebaseUser?.uid?:"")
        },
        onEditClick = {
            mainViewModel.selectedHabitTracker=it
            viewModel.clearStates()
            onNavigate(ScreensRoute.EditHabitTracker.route)
        },
        listHabitState=listHabitState,
        deleteHabitState=deleteHabitState,
        updateHabitState=updateHabitState,
        updateHabit={
        viewModel.updateHabit(it)
    }, onBackClick = onBackClick)
}

@Composable
internal fun HabitTrackerScreen(
    promptsViewModel: PromptsViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit,
    updateHabit:(HabitTrackerModel)->Unit = {},
    onDeleteClick:(title:String)->Unit = {},
    onEditClick:(item:HabitTrackerModel)->Unit = {},
    listHabitState:ResponseStates<List<HabitTrackerModel>> = ResponseStates.Idle,
    updateHabitState:ResponseStates<String> = ResponseStates.Idle,
    deleteHabitState:ResponseStates<String> = ResponseStates.Idle,
    onBackClick: () -> Unit
) {

    val context = LocalContext.current
    val currentPrompt by promptsViewModel.currentPrompt.collectAsStateWithLifecycle()

    val mediaPlayer = remember { MediaPlayer() }
    var showEditDeleteIcons:Boolean by remember { mutableStateOf(false) }
    var listHabitTracker: List<HabitTrackerModel> by remember { mutableStateOf(listOf()) }
    ScreenContainer(currentPrompt = currentPrompt) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(title = "Your Habits") {
                onBackClick()
            }
            SpacerHeight(3.hpr)
            if(listHabitTracker.isNotEmpty()) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    ResourceImage(image = if(showEditDeleteIcons){ R.drawable.ic_cross}else{ R.drawable.ic_edit}, modifier = Modifier.safeClickable { showEditDeleteIcons=!showEditDeleteIcons }.size(22.sdp))
                }
            }else{
                SpacerHeight(2.hpr)
            }
            if(listHabitTracker.isEmpty()){
                Column (modifier = Modifier.fillMaxWidth().weight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                    ResourceImage(image = R.drawable.ic_info,modifier = Modifier.size(24.sdp))
                    SpacerHeight(5.sdp)
                    TitleSmallText(text = "Currently you don't have any habits to track")
                }
            }else{
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
                            showEditDeleteIcons=showEditDeleteIcons,
                            item = item,
                            onDeleteClick = {
                                showEditDeleteIcons=false
                                onDeleteClick(item.title)
                            },
                            onEditClick = {
                                showEditDeleteIcons=false
                                onEditClick(item)
                            }
                        ) {
                            if(item.completedPerDay<item.totalPerDay){
                                playSuccessTune(context=context,mediaPlayer=mediaPlayer)
                                listHabitTracker[index].completedPerDay++
                                showEditDeleteIcons=false
                                updateHabit(listHabitTracker[index])
                            }
                        }
                    }
                }
                SpacerWeight(1f)
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                ResourceImage(modifier = Modifier
                    .size(45.sdp)
                    .safeClickable {
                        onNavigate(ScreensRoute.SelectHabitTracker.route)
                    }, image = R.drawable.ic_add)
            }
            SpacerHeight(1.hpr)

        }
    }

    HandleApiStates(
        state = listHabitState, updatePrompt = promptsViewModel::updatePrompt
    ) { it ->
        LaunchedEffect(Unit) {
            if(it.isNotEmpty()){
                HabitReminderScheduler.setHabitReminder(context = context, habitList = it)
                context.showToast("Press and hold to complete your today habit task")
            }else{
                HabitReminderScheduler.cancelAllHabitReminders(context = context)
            }
            listHabitTracker = it
        }
    }

    HandleApiStates(
        state = updateHabitState, updatePrompt = promptsViewModel::updatePrompt
    ) { it -> }


    HandleApiStates(
        state = deleteHabitState, updatePrompt = promptsViewModel::updatePrompt
    ) { it ->

    }
}



fun playSuccessTune(context: Context, mediaPlayer: MediaPlayer) {
    try {
        val notificationSoundUri = Uri.parse(
            "android.resource://" + context.packageName + "/" + R.raw.tune_good // Replace with your sound file
        )

        mediaPlayer.reset()
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
        )
        mediaPlayer.setDataSource(context, notificationSoundUri)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener { mp ->
            mp.start()
        }
        mediaPlayer.setOnCompletionListener { mp ->
            mp.reset() //reset to avoid memory leaks
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@Preview(showBackground = true)
@Composable
fun HabitScreenPreview() {
    HabitTrackerScreen(onNavigate = {}, onBackClick = {})
}
