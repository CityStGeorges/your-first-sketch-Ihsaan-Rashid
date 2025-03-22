package com.example.moodbloom.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import com.example.moodbloom.R
import com.example.moodbloom.utils.extension.SpacerWidth
import com.example.moodbloom.ui.theme.md_theme_light_disable
import com.example.moodbloom.ui.typo.BodyLargeText

@Composable
fun DropDownField(text: String, placeholder: String) {
    CardContainer {
        Row(
            Modifier
                .fillMaxWidth()
                .height(40.sdp)
                .padding(start = 10.sdp, end = 10.sdp, top = 3.sdp, bottom = 3.sdp)
        ) {
            if (text.isBlank()) {
                BodyLargeText(text = placeholder, overrideColor = md_theme_light_disable)
            } else {
                BodyLargeText(text = text)
            }
            ResourceImage(image = R.drawable.ic_dropdown, modifier = Modifier.fillMaxHeight())
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T>DropdownSelectionField(
    label: String = "",
    placeholder: String = "",
    list: List<T>, selectedOption: String="",
    onValueChange: (Int,T) -> Unit,
    dropDownItem: @Composable (Int,T) -> Unit = {_,_->}
) {

    var expanded by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }  // Fix for focus issues
    val focusManager = LocalFocusManager.current  // Helps manage focus

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextInputField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            placeholder = placeholder,
            label = label,
            singleLine = true,
            trailing = { ResourceImage(image = R.drawable.ic_dropdown, modifier = Modifier.size(17.sdp))},
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .focusRequester(focusRequester)  // Ensure focusRequester is used
                .clickable { expanded = true }
        )

        ExposedDropdownMenu(
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background),
            expanded = expanded,
            onDismissRequest = { expanded = false
                focusManager.clearFocus() }
        ) {
            list.forEachIndexed {index, option ->
                DropdownMenuItem(
                  modifier =   Modifier.fillMaxWidth(),
                    text = {
                       dropDownItem(index,option)
                    },
                    onClick = {
                        onValueChange(index,option)
                        focusManager.clearFocus()
                        expanded = false
                    }
                )
            }
        }
    }
}
