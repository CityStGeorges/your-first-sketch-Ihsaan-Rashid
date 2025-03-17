package com.example.moodbloom.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    label: String = "",
    borderColor: Color = MaterialTheme.colorScheme.outline,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    inputType: InputType = InputType.Any,
    leading: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    minLines: Int = 1,
    maxLines: Int = 1,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    shape: Shape = MaterialTheme.shapes.medium,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            val filteredText = filterInput(it, inputType)
            onValueChange(filteredText)
        },
        label = { if (label.isNotEmpty()) Text(label) },
        placeholder = { if (placeholder.isNotEmpty()) Text(placeholder) },
        leadingIcon = leading,
        trailingIcon = trailing,
        textStyle = textStyle.copy(color = textColor),
        shape = shape,
        maxLines = maxLines,
        minLines = minLines,
        singleLine = singleLine,
        enabled = enabled,
        readOnly = readOnly,
        keyboardOptions = getKeyboardOptions(inputType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = borderColor,
            unfocusedBorderColor = borderColor.copy(alpha = 0.5f),
            cursorColor = textColor
        ),
        modifier = modifier.fillMaxWidth()
    )
}

sealed class InputType {
    data object Any : InputType()
    data object IsDigit : InputType()
    data object IsLetterOrDigit : InputType()
    data object IsLetterOrDigitWithoutSpace : InputType()
    data object IsLetterOrDigitWithSpace : InputType()
    data object IsLetter : InputType()
    data object IsSpecialCharacter : InputType()
}

// Function to filter input based on InputType
fun filterInput(input: String, type: InputType): String {
    return when (type) {
        InputType.Any -> input
        InputType.IsDigit -> input.filter { it.isDigit() }
        InputType.IsLetterOrDigit -> input.filter { it.isLetterOrDigit() }
        InputType.IsLetterOrDigitWithoutSpace -> input.filter { it.isLetterOrDigit() && !it.isWhitespace() }
        InputType.IsLetterOrDigitWithSpace -> input.filter { it.isLetterOrDigit() || it.isWhitespace() }
        InputType.IsLetter -> input.filter { it.isLetter() }
        InputType.IsSpecialCharacter -> input.filterNot { it.isLetterOrDigit() || it.isWhitespace() }
    }
}

// Function to get keyboard options based on input type
fun getKeyboardOptions(type: InputType): KeyboardOptions {
    return when (type) {
        InputType.Any -> KeyboardOptions.Default
        InputType.IsDigit -> KeyboardOptions(keyboardType = KeyboardType.Number)
        InputType.IsLetterOrDigit,
        InputType.IsLetterOrDigitWithoutSpace,
        InputType.IsLetterOrDigitWithSpace,
        InputType.IsLetter -> KeyboardOptions(keyboardType = KeyboardType.Text)
        InputType.IsSpecialCharacter -> KeyboardOptions(keyboardType = KeyboardType.Ascii)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTextInputField() {
    var text by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextInputField(
            value = text,
            onValueChange = { text = it },
            placeholder = "Enter your text",
            label = "Input",
            borderColor = Color.Blue,
            textColor = Color.Black,
            inputType = InputType.IsLetterOrDigit,
            leading = { Icon(Icons.Default.Person, contentDescription = "Person") },
            trailing = { Icon(Icons.Default.Clear, contentDescription = "Clear") }
        )


        TextInputField(
            value = text,
            onValueChange = { text = it },
            placeholder = "Enter your text",
            label = "Input",
            borderColor = Color.Blue,
            textColor = Color.Black,
            inputType = InputType.IsLetterOrDigit
        )
    }
}
