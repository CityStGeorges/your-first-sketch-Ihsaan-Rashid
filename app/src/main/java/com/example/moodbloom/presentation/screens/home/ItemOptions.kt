package com.example.moodbloom.presentation.screens.home

import android.graphics.Color.parseColor
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.moodbloom.R
import com.example.moodbloom.domain.models.HomeOptionsModel
import com.example.moodbloom.extension.SpacerWeight
import com.example.moodbloom.extension.SpacerWidth
import com.example.moodbloom.presentation.components.ResourceImage
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.ui.typo.TitleSmallText

@Composable
fun ItemOptions(
    modifier: Modifier = Modifier,
    item: HomeOptionsModel
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(vertical = 10.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ResourceImage(image = item.icon, modifier = Modifier.size(25.sdp))
        SpacerWidth(15.sdp)
        TitleSmallText(text = item.title)
        SpacerWeight(1f)
        ResourceImage(image = R.drawable.ic_arrow, modifier = Modifier.size(25.sdp))
    }
}

@Composable
fun ItemOptionNotification(
    modifier: Modifier = Modifier,
    item: HomeOptionsModel,
    isChecked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        ResourceImage(image = item.icon, modifier = Modifier.size(25.sdp))
        SpacerWidth(15.sdp)
        TitleSmallText(text = if (isChecked) "Turn off notification" else item.title)
        SpacerWeight(1f)
        Switch(
            checked = isChecked, onCheckedChange = onCheckedChange, colors = SwitchDefaults.colors(
                checkedThumbColor = Color(parseColor("#3076F0")),
                uncheckedThumbColor = Color(parseColor("#C7C7C7")),
                checkedTrackColor = Color(parseColor("#E2E1E1")),
                uncheckedTrackColor = Color(parseColor("#E2E1E1"))
            )
        )
    }
}


@Composable
fun ItemSwitcher(
    modifier: Modifier = Modifier,
    item: String,
    isChecked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        TitleSmallText(text = item)
        SpacerWeight(1f)
        Switch(
            checked = isChecked, onCheckedChange = onCheckedChange, colors = SwitchDefaults.colors(
                checkedThumbColor = Color(parseColor("#3076F0")),
                uncheckedThumbColor = Color(parseColor("#C7C7C7")),
                checkedTrackColor = Color(parseColor("#E2E1E1")),
                uncheckedTrackColor = Color(parseColor("#E2E1E1"))
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItemOptions() {
    Column {
        ItemOptions(
            item = HomeOptionsModel(
                title = "Test Title",
                R.drawable.ic_habit_tracker,
                route = ""
            )
        )
        ItemOptionNotification(
            item = HomeOptionsModel(
                title = "Enable Notification",
                R.drawable.ic_bell,
                route = ""
            )
        )
    }

}