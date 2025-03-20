package com.example.moodbloom.presentation.screens.habittracker


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
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
import com.example.moodbloom.data.DataHelper.getDaysList
import com.example.moodbloom.domain.models.HabitTrackerModel
import com.example.moodbloom.extension.ResponseStates
import com.example.moodbloom.extension.SpacerHeight
import com.example.moodbloom.extension.SpacerWeight
import com.example.moodbloom.extension.SpacerWidth
import com.example.moodbloom.presentation.components.CardContainer
import com.example.moodbloom.presentation.components.HandleApiStates
import com.example.moodbloom.presentation.components.InputType
import com.example.moodbloom.presentation.components.PromptsViewModel
import com.example.moodbloom.presentation.components.ResourceImage
import com.example.moodbloom.presentation.components.ScreenContainer
import com.example.moodbloom.presentation.components.TextButton
import com.example.moodbloom.presentation.components.TextInputField
import com.example.moodbloom.presentation.components.TopAppBar
import com.example.moodbloom.presentation.components.hpr
import com.example.moodbloom.presentation.components.safeClickable
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.presentation.screens.habittracker.items.ItemDays
import com.example.moodbloom.ui.typo.TitleSmallText
import java.util.Calendar


@Composable
fun UpdateHabitTrackerRoute(
    onNavigate: () -> Unit,
    onBackClick: () -> Unit,
    mainViewModel: MainViewModel,
    viewModel: HabitTrackerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val updateHabitState by viewModel.updateHabitState.collectAsStateWithLifecycle()
    UpdateHabitTrackerScreen(
        selectedHabit = mainViewModel.selectedHabitTracker,
        updateHabitState = updateHabitState,
        onNavigate = {
            viewModel.clearStates()
            onNavigate()
        },
        userId = mainViewModel.firebaseUser?.uid ?: "",
        updateHabit = viewModel::updateHabit,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UpdateHabitTrackerScreen(
    promptsViewModel: PromptsViewModel = hiltViewModel(),
    selectedHabit: HabitTrackerModel? = null,
    updateHabitState: ResponseStates<String> = ResponseStates.Idle,
    updateHabit: (HabitTrackerModel) -> Unit = {},
    userId: String = "",
    onNavigate: () -> Unit,
    onBackClick: () -> Unit
) {

    val context = LocalContext.current
    val currentPrompt by promptsViewModel.currentPrompt.collectAsStateWithLifecycle()

    var habitName by remember { mutableStateOf(selectedHabit?.title ?: "") }
    val currentTime = Calendar.getInstance()

    var timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )


    var showTimePicker by remember { mutableStateOf(false) }
    var executionPerDay by remember { mutableStateOf(1) }

    var listDays by remember {
        mutableStateOf(getDaysList())
    }
    var listReminders:MutableList<String> by remember { mutableStateOf(mutableListOf()) }
    var selectedIndex=-1
    LaunchedEffect(selectedHabit) {
        listReminders = selectedHabit?.reminderTimes?.toMutableList() ?: mutableListOf()
        listDays = listDays.map { daysModel ->
            daysModel.copy(
                isSelected = selectedHabit?.selectedDays?.contains(daysModel.title) ?: false
            )
        }
    }

    ScreenContainer(currentPrompt = currentPrompt) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(title = "Update Habit") {
                onBackClick()
            }
            SpacerHeight(5.hpr)
            CardContainer(
                containerColor = MaterialTheme.colorScheme.background,
                borderColor = MaterialTheme.colorScheme.outline
            ) {
                Column(
                    modifier = Modifier
                        .size(70.sdp)
                        .padding(10.sdp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ResourceImage(
                        image = R.drawable.ic_habit_custom,
                        modifier = Modifier
                            .size(45.sdp)
                    )
                }
            }
            SpacerHeight(20.sdp)
            TextInputField(
                value = habitName,
                onValueChange = { habitName = it },
                singleLine = true,
                placeholder = "Enter habit name",
                label = "Habit Name",
                readOnly = true,
                inputType = InputType.IsLetterOrDigitWithSpace
            )
            SpacerHeight(10.sdp)
            CardContainer(
                containerColor = MaterialTheme.colorScheme.background,
                borderColor = MaterialTheme.colorScheme.outline
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.sdp)
                ) {
                    TitleSmallText(text = "Select Days")
                    SpacerHeight(10.sdp)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(5.sdp)) {
                        itemsIndexed(listDays) { index, item ->
                            ItemDays(
                                isSelected = item.isSelected, day = item.title,
                                modifier = Modifier
                                    .size(45.sdp)
                                    .safeClickable {
                                        listDays = listDays.mapIndexed { i, day ->
                                            if (i == index) day.copy(isSelected = !day.isSelected) else day
                                        }
                                    },
                            )
                        }
                    }
                }

            }
            SpacerHeight(10.sdp)
            CardContainer(
                containerColor = MaterialTheme.colorScheme.background,
                borderColor = MaterialTheme.colorScheme.outline
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.sdp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TitleSmallText(text = "$executionPerDay Execution per day")
                    SpacerWeight(1f)
                    ResourceImage(
                        image = R.drawable.ic_minus,
                        modifier = Modifier
                            .size(22.sdp)
                            .clickable {
                                if (executionPerDay > 1) {
                                    executionPerDay--
                                }
                            })
                    SpacerWidth(2.sdp)
                    ResourceImage(
                        image = R.drawable.ic_plus,
                        modifier = Modifier
                            .size(22.sdp)
                            .clickable {
                                if (executionPerDay < 10) {
                                    executionPerDay++
                                }
                            })
                }
            }
            SpacerHeight(10.sdp)
            listReminders.forEachIndexed { index, item ->
                CardContainer(
                    containerColor = MaterialTheme.colorScheme.background,
                    borderColor = MaterialTheme.colorScheme.outline
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.sdp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TitleSmallText(
                            text = item,
                            modifier = Modifier.safeClickable {
                                timePickerState = TimePickerState(
                                    initialHour = item.substringBefore(":").toInt()?:0,
                                    initialMinute = item.substringAfter(":").toInt()?:0,
                                    is24Hour = true,
                                )
                                selectedIndex=index
                                showTimePicker = true

                            })
                        SpacerWeight(1f)
                        ResourceImage(
                            image = R.drawable.ic_delete,
                            modifier = Modifier
                                .size(22.sdp)
                                .safeClickable {
                                    listReminders.remove(item)
                                }
                        )
                    }
                }
                SpacerHeight(10.sdp)
            }
            CardContainer(
                modifier = Modifier.safeClickable {
                    showTimePicker = true
                    selectedIndex=-1
                },
                containerColor = MaterialTheme.colorScheme.background,
                borderColor = MaterialTheme.colorScheme.outline
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.sdp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TitleSmallText(text = "Add Reminder")
                    SpacerWeight(1f)
                    ResourceImage(
                        image = R.drawable.ic_bell_reminder,
                        modifier = Modifier.size(22.sdp)
                    )
                }
            }
            SpacerWeight(1f)
            TextButton(enabled = listDays.find { it.isSelected } != null && habitName.length > 2,
                modifier = Modifier.padding(horizontal = 10.sdp),
                text = "Add To My Habits",
                onClick = {
                    if (listDays.find { it.isSelected } != null) {
                        updateHabit(HabitTrackerModel(
                            userId = userId,
                            title = habitName,
                            iconUrl = selectedHabit?.iconUrl ?: "",
                            selectedDays = listDays.filter { it.isSelected }.map { it.title },
                            totalPerDay = executionPerDay,
                            completedPerDay = selectedHabit?.completedPerDay?:0,
                            reminderTimes = listReminders.toList()
                        )
                        )
                    }
                })
            SpacerHeight(5.hpr)
        }
        if (showTimePicker) {
            Box {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CardContainer(
                        containerColor = MaterialTheme.colorScheme.background,
                        borderColor = MaterialTheme.colorScheme.outline
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            SpacerHeight(20.sdp)
                            TimePicker(state = timePickerState, colors = TimePickerDefaults.colors(containerColor=MaterialTheme.colorScheme.primary))
                            SpacerHeight(10.sdp)
                            Row(modifier = Modifier.fillMaxWidth()) {
                                TextButton(modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 10.sdp),
                                    text = "Set Time",
                                    onClick = {
                                        showTimePicker = false
                                        if (selectedIndex!=-1 && selectedIndex<listReminders.size){
                                            listReminders[selectedIndex]="${timePickerState.hour.toString().takeIf { it.length==2 }?:"0${timePickerState.hour}"}:${timePickerState.minute.toString().takeIf { it.length==2 }?:"0${timePickerState.minute}"}"
                                        }else{
                                            listReminders.add("${timePickerState.hour.toString().takeIf { it.length==2 }?:"0${timePickerState.hour}"}:${timePickerState.minute.toString().takeIf { it.length==2 }?:"0${timePickerState.minute}"}" )
                                        }
                                        selectedIndex=-1
                                    })
                                SpacerWidth(5.sdp)
                                TextButton(modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 10.sdp),
                                    text = "Cancel",
                                    onClick = {
                                        showTimePicker = false
                                        selectedIndex=-1
                                    })
                            }
                            SpacerHeight(10.sdp)
                        }
                    }
                }
            }

        }
    }
    HandleApiStates(
        state = updateHabitState, updatePrompt = promptsViewModel::updatePrompt
    ) { it ->
        LaunchedEffect(Unit) {
            onNavigate()
        }
    }
}


@Preview(showBackground = true)
@Composable
internal fun UpdateHabitTrackerPreview() {
    UpdateHabitTrackerScreen(onNavigate = {}, onBackClick = {})
}
