package com.example.moodbloom.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.moodbloom.R
import com.example.moodbloom.ui.theme.AppTheme


@Composable
fun ScreenContainer(
    modifier: Modifier = Modifier,
    currentPrompt: PromptTypeShow?=null,
    horizontalPadding: Dp = 15.sdp,
    content: @Composable (() -> Unit),
) {
    //val keyboardController = LocalSoftwareKeyboardController.current
    Box(
        modifier
            .fillMaxSize()
            .padding(horizontal = horizontalPadding)) {
        content()
    }
    currentPrompt?.let { prompt ->
        when (prompt) {
            is PromptTypeShow.Loading -> {
                ShowLoader()
            }
            is PromptTypeShow.Error -> {
                ShowError(
                    title = prompt.title,
                    message = prompt.message,
                    onDragClose = prompt.onDragClose,
                    img = prompt.img?: R.drawable.ic_error,
                    buttonText = prompt.positiveButtonText,
                    buttonClick = prompt.positiveButtonClick,
                    cancelable = prompt.cancelable,
                    onDismiss =prompt.onDismiss
                )
            }
            is PromptTypeShow.Warning -> {
                ShowError(
                    title = prompt.title,
                    message = prompt.message,
                    onDragClose = prompt.onDragClose,
                    img = prompt.img?: R.drawable.ic_info,
                    buttonText = prompt.positiveButtonText,
                    buttonClick = prompt.positiveButtonClick,
                    cancelable = prompt.cancelable,
                    onDismiss =prompt.onDismiss
                )
            }
            is PromptTypeShow.Success -> {
                ShowSuccess(
                    title = prompt.title,
                    message = prompt.message,
                    onDragClose = prompt.onDragClose,
                    img = prompt.img?: R.drawable.ic_success,
                    buttonText = prompt.positiveButtonText,
                    cancelable = prompt.cancelable,
                    buttonClick = prompt.positiveButtonClick,
                    onDismiss = prompt.onDismiss,
                )
            }
            is PromptTypeShow.ComingSoon -> {
                ShowError(
                    title = prompt.title,
                    message = prompt.message,
                    onDragClose = prompt.onDragClose,
                    img = prompt.img?: R.mipmap.ic_launcher,
                    buttonText = prompt.positiveButtonText,
                    buttonClick = prompt.positiveButtonClick,
                    cancelable = prompt.cancelable,
                    onDismiss =prompt.onDismiss
                )
            }
            is PromptTypeShow.Confirmation -> {
                ShowConfirmation(
                    title = prompt.title,
                    message = prompt.message,
                    onDragClose = prompt.onDragClose,
                    img = prompt.img?: R.mipmap.ic_launcher,
                    positiveButtonText = prompt.positiveButtonText,
                    negativeButtonText = prompt.negativeButtonText,
                    positiveButtonClick = prompt.positiveButtonClick,
                    negativeButtonClick = prompt.negativeButtonClick,
                    cancelable = prompt.cancelable,
                    onDismiss =prompt.onDismiss
                )
            }
            else->{}
        }
    }
}
sealed class PromptTypeShow(
    val title: String?,
    val message: String?,
    val cancelable: Boolean = true,
    val onDragClose: Boolean = false,
    val img: Int? = null,
    val positiveButtonText: String = "Okay",
    val negativeButtonText: String = "Okay",
    val onDismiss: () -> Unit,
    val positiveButtonClick: () -> Unit,
    val negativeButtonClick: () -> Unit = {},
) {
    class Loading: PromptTypeShow(title = null, message = null, cancelable = false, img = null, onDismiss = {}, positiveButtonClick = {})
    class Error(
        title: String? = null,
        message: String? = null,
        cancelable: Boolean = true,
        onDragClose: Boolean = true,
        img: Int? = null,
        buttonText: String = "Okay",
        onDismiss: () -> Unit,
        onButtonClick: () -> Unit
    ) : PromptTypeShow(
        title = title,
        message = message.takeIf { !it.isNullOrEmpty() }?:"We are unable to process your request at this time. Please try again later.",
        cancelable = cancelable,
        onDragClose=onDragClose,
        img = img,
        positiveButtonText = buttonText,
        onDismiss = onDismiss,
        positiveButtonClick = onButtonClick
    )

    class Success(
        title: String? = null,
        message: String? = null,
        cancelable: Boolean = true,
        onDragClose: Boolean = true,
        img: Int? = null,
        buttonText: String = "Okay",
        onDismiss: () -> Unit,
        onButtonClick: () -> Unit
    ) : PromptTypeShow(
        title = title,
        message = message,
        cancelable = cancelable,
        onDragClose=onDragClose,
        img = img,
        positiveButtonText = buttonText,
        onDismiss = onDismiss,
        positiveButtonClick = onButtonClick
    )

    class Warning(
        title: String? = null,
        message: String? = null,
        cancelable: Boolean = true,
        onDragClose: Boolean = true,
        img: Int? = null,
        buttonText: String = "Okay",
        onDismiss: () -> Unit,
        onButtonClick: () -> Unit
    ) : PromptTypeShow(
        title = title,
        message = message,
        cancelable = cancelable,
        onDragClose=onDragClose,
        img = img,
        positiveButtonText = buttonText,
        onDismiss = onDismiss,
        positiveButtonClick = onButtonClick
    )

    class ComingSoon(
        title: String? = null,
        message: String = "Coming Soon",
        cancelable: Boolean = true,
        onDragClose: Boolean = true,
        img: Int? = null,
        buttonText: String = "Okay",
        onButtonClick: () -> Unit={},
        onDismiss: () -> Unit,
    ) : PromptTypeShow(
        title = title,
        message = message,
        cancelable = cancelable,
        onDragClose=onDragClose,
        img = img,
        positiveButtonText = buttonText,
        onDismiss = onDismiss,
        positiveButtonClick = onButtonClick
    )

    class Confirmation(
        title: String? = null,
        message: String? = null,
        cancelable: Boolean = true,
        onDragClose: Boolean = true,
        img: Int? = null,
        positiveButtonText: String = "Yes",
        negativeButtonText: String = "No",
        onDismiss: () -> Unit,
        positiveButtonClick: () -> Unit = {},
        negativeButtonClick: () -> Unit = {}
    ) : PromptTypeShow(
        title = title,
        message = message,
        cancelable = cancelable,
        onDragClose=onDragClose,
        img = img,
        positiveButtonText = positiveButtonText,
        negativeButtonText = negativeButtonText,
        onDismiss = onDismiss,
        positiveButtonClick = positiveButtonClick,
        negativeButtonClick = negativeButtonClick
    )
    class UnAthorized(title: String) : PromptTypeShow(
        title = title,
        message = null,
        cancelable = false,
        img = null,
        onDismiss = {},
        positiveButtonClick = {}
    )


}

@Preview(name = "ScreenContainer")
@Composable
private fun PreviewScreenContainer() {
    ScreenContainer{

    }
}

@Composable
fun PreviewWrapper(
    content: @Composable () -> Unit = {},
) {
    AppTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column {
                content()
            }
        }
    }
}

@Composable fun AppBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    center = Offset.Infinite.copy(y = 0f),
                    colors = listOf(Color(0xFF13455e), Color(0xFF050f18)),
                )
            )
    )
}