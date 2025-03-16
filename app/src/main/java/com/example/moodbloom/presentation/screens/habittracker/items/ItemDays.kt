package com.example.moodbloom.presentation.screens.habittracker.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.moodbloom.presentation.components.CardContainer
import com.example.moodbloom.ui.theme.md_theme_light_disable_outline
import com.example.moodbloom.ui.typo.TitleSmallText


@Composable
fun ItemDays(modifier:Modifier = Modifier,isSelected: Boolean, day: String) {

    CardContainer(
        shape = MaterialTheme.shapes.extraSmall,
        containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
        borderColor = if (isSelected) MaterialTheme.colorScheme.primary else md_theme_light_disable_outline,
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TitleSmallText(text = day, overrideColor = if (isSelected) Color.White else Color.Black)
        }
    }
}


@Preview(showBackground = true)
@Composable
internal fun ItemDaysPreview() {
    ItemDays(isSelected = false, day = "M")
}
