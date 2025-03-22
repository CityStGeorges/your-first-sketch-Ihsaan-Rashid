package com.example.moodbloom.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.moodbloom.R
import com.example.moodbloom.utils.extension.SpacerHeight
import com.example.moodbloom.ui.typo.TitleLargeText

@Composable
fun TopAppBar(
    showBackIcon: Boolean = true,
    title: String = "",
    topSpace: Int = 5,
    onBackClick: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth()){
        SpacerHeight(topSpace.sdp)
        if (showBackIcon) {
            ResourceImage(
                image = R.drawable.ic_back,
                modifier = Modifier.safeClickable { onBackClick() })
        }
        SpacerHeight(10.sdp)
        if (title.isNotBlank()) {
            TitleLargeText(text = title)
        }
    }
}