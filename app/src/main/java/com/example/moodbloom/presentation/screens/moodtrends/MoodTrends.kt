package com.example.moodbloom.presentation.screens.moodtrends

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.example.moodbloom.MainViewModel
import com.example.moodbloom.R
import com.example.moodbloom.domain.models.DateModel
import com.example.moodbloom.extension.SpacerHeight
import com.example.moodbloom.extension.SpacerWidth
import com.example.moodbloom.presentation.components.BaseButton
import com.example.moodbloom.presentation.components.CardContainer
import com.example.moodbloom.presentation.components.PromptsViewModel
import com.example.moodbloom.presentation.components.ResourceImage
import com.example.moodbloom.presentation.components.ScreenContainer
import com.example.moodbloom.presentation.components.TopAppBar
import com.example.moodbloom.presentation.components.hpr
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.ui.typo.BodySmallText
import com.example.moodbloom.ui.typo.TitleLargeText
import com.example.moodbloom.ui.typo.TitleMediumText
import com.example.moodbloom.ui.typo.TitleSmallText

@Composable
fun MoodTrendsScreenRoute(
    onNavigate: (String) -> Unit,
    onBackClick: () -> Unit,
    mainViewModel: MainViewModel,
    viewModel: MoodsTrendsViewModel = hiltViewModel()
) {
    val chartData by viewModel.chartData.collectAsStateWithLifecycle()
    MoodTrendsScreen(
        onNavigate = onNavigate,
        onBackClick = onBackClick,
        getLastDates = viewModel::getLastDates,
        chartData = chartData
    )
    LaunchedEffect(Unit) {
        viewModel.getLastDates("Daily")
    }
}

@Composable
internal fun MoodTrendsScreen(
    promptsViewModel: PromptsViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {},
    chartData: List<DateModel> = listOf(),
    getLastDates: (String) -> Unit = { _ -> },

    onBackClick: () -> Unit
) {
    var habitInsights by remember { mutableStateOf("This is the testing Insights Text djfk  ksjdfj koj dsfk kjdsf aksejf mnf urmds the  sdjfh sdfjhuydsa fjsdf lkjds fsdnsdmf djfh") }
    val currentPrompt by promptsViewModel.currentPrompt.collectAsStateWithLifecycle()
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp

    val yAxisImages = listOf(
        R.drawable.emoji_very_happy, // 0
        R.drawable.emoji_happy, // 1
        R.drawable.emoji_natural, // 2
        R.drawable.emoji_uncertainity, // 3
        R.drawable.emoji_sad, // 4
        R.drawable.emoji_verysad, // 5
        R.drawable.emoji_angry, // 6
    )

    val chartTimeOption = listOf(
        "Daily", "Weekly", "Monthly"
    )
    var dataShowOption by remember { mutableStateOf("Daily") }


    // Function to get labels dynamically
    val pointsData: List<Point> = chartData.mapIndexed { index, dateModel ->
        Point(
            index.toFloat(),
            dateModel.modeScore.toFloat()
        )
    }

    val xAxisData = AxisData.Builder()
        .startDrawPadding(0.dp)
        .axisStepSize((screenWidthDp / 8) - 2.dp)
        .axisLineColor(MaterialTheme.colorScheme.surfaceVariant)
        .axisLineThickness(1.dp)
        .steps(pointsData.size - 1)
        .labelData { i ->
            if (i < chartData.size) {
                chartData[i].label
            } else {
                ""
            }
        }
        .labelAndAxisLinePadding(15.dp)
        .build()


    val yAxisData = AxisData.Builder()
        .steps(6).axisLineColor(MaterialTheme.colorScheme.surfaceVariant)
        .axisLineThickness(2.dp)
        /*.backgroundColor(Color.Red)*/
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            val yScale = 100 / pointsData.size
            (i * yScale).toString()
        }.build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(color = MaterialTheme.colorScheme.primary),
                    IntersectionPoint(color = MaterialTheme.colorScheme.primary),
                    SelectionHighlightPoint(color = MaterialTheme.colorScheme.primary),
                    ShadowUnderLine(color = MaterialTheme.colorScheme.primary),
                    SelectionHighlightPopUp(
                        popUpLabel = { x, y ->
                            val xLabel = "x : ${x.toInt()} "
                            val yLabel = "y : ${String.format("%.2f", y)}"
                             "${textToEmoji(y)} $xLabel $yLabel"
                        },
                    )
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        isZoomAllowed = false,


        /*  gridLines = GridLines(),*/
        /*backgroundColor = Color.Red*/
    )
    ScreenContainer(currentPrompt = currentPrompt) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(title = "Mood Trends") {
                onBackClick()
            }
            SpacerHeight(5.hpr)
            OutlinedCard(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.sdp, end = 3.sdp, top = 5.sdp, bottom = 10.sdp)
                ) {
                    Box(contentAlignment = Alignment.TopStart) {
                        if (pointsData.isNotEmpty()) {
                            LineChart(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.sdp),
                                lineChartData = lineChartData
                            )
                            LazyColumn(
                                modifier = Modifier.background(color = Color.White),
                                verticalArrangement = Arrangement.spacedBy(4.sdp)
                            ) {
                                itemsIndexed(yAxisImages) { index, item ->
                                    if (index == 0) {
                                        SpacerHeight(15.sdp)
                                    }
                                    Row {
                                        ResourceImage(
                                            image = item,
                                            modifier = Modifier.size(20.sdp)
                                        )
                                        SpacerWidth(2.sdp)
                                    }
                                }
                            }
                        }

                    }
                    SpacerHeight(10.sdp)
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        itemsIndexed(chartTimeOption) { index, item ->
                            BaseButton(
                                modifier = Modifier
                                    .width(80.sdp)
                                    .height(30.dp),
                                shape = MaterialTheme.shapes.extraSmall,
                                primary = item == dataShowOption,
                                enabled = true,
                                content = {
                                    Text(
                                        modifier = Modifier,
                                        text = item, style = MaterialTheme.typography.labelSmall
                                    )
                                },
                                onClick = {
                                    if (dataShowOption != item) {
                                        dataShowOption = item
                                        getLastDates(item)
                                    }
                                }
                            )

                        }

                    }


                }
            }
            SpacerHeight(2.hpr)
            TitleLargeText(text = "Insights")
            SpacerHeight(1.hpr)
            CardContainer{
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.sdp)
            ) {
                SpacerHeight(10.sdp)
                BodySmallText(text =habitInsights, modifier = Modifier.fillMaxWidth().heightIn(min = 200.sdp),)
            }
        }
        }
    }
}

fun textToEmoji(score: Float): String {
    return when (score) {
        0f -> "\uD83D\uDE21" // 😡
        1f -> "\uD83D\uDE22" // 😭
        2f -> "\uD83D\uDE1E" // 😞
        3f -> "\uD83E\uDD14" // 🤔
        4f -> "\uD83D\uDE10"  // 😐
        5f -> "\uD83D\uDE0A" // 🙂
        6f -> "\uD83D\uDE0D" // 😍
        else -> "\uD83D\uDE10" // 🙂
    }

}
@Preview(showBackground = true)
@Composable
fun PreviewMoodTrendsScreen() {
    MoodTrendsScreen {}
}