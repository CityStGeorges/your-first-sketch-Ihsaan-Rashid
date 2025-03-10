package com.example.moodbloom.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.moodbloom.R
import com.example.moodbloom.extension.SpacerWidth
import com.example.moodbloom.ui.theme.AppTheme
import com.example.moodbloom.ui.typo.LabelLargeText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowBaseModalSheet(
    modifier: Modifier = Modifier,
    cancelable: Boolean = true,
    onDragClose: Boolean = true,
    onCloseClicked: (() -> Unit)? = null,
    onDismissRequest: (() -> Unit)? = null,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    sheetState: SheetState = rememberModalBottomSheetState(),
    content: @Composable () -> Unit
) {
    if (sheetState.isVisible) {
        ModalBottomSheet(modifier = modifier.padding(
            top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
        ).windowInsetsPadding(WindowInsets.ime),
            sheetState = sheetState,
            dragHandle = {},
            containerColor = MaterialTheme.colorScheme.surface,
            content = {
                CloseWrapper(
                    canClose = cancelable, onCloseClicked = onCloseClicked
                ) {
                    content()
                }
            },
           /* windowInsets = WindowInsets.ime,*/
            onDismissRequest = {
                if(onDragClose){
                    onDismissRequest?.invoke()
                }else{
                    coroutineScope.launch {
                        sheetState.show()
                    }
                }
            })
    }
}

/**
 * Shows a prompt modal bottom sheet
 *
 * @param modifier Modifier that applies to root composable
 * @param type Type of prompt, use values given in [PromptType]
 * @param options Options for prompt, use values given in [PromptOptions]
 * @param title Title of prompt
 * @param message Message of prompt
 * @param cancelable specifies whether the prompt can be cancelled or not
 * @param onResult Callback that returns the selected option. Use values given in [PromptResults]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ShowPrompt(
    modifier: Modifier = Modifier,
    img: T? = null,
    title: String? = null,
    message: String? = null,
    sheetState: SheetState = rememberModalBottomSheetState(),
    content: @Composable (() -> Unit)? = null,
    positiveButton: @Composable (() -> Unit)? = null,
    negativeButton: @Composable (() -> Unit)? = null,
    cancelable: Boolean = true,
    onDragClose: Boolean = true,
    onDismissRequest: (() -> Unit)? = null,
    onCloseClick: (() -> Unit)? = null,
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    ShowBaseModalSheet(modifier = modifier,
        sheetState = sheetState,
        cancelable = cancelable,
        onDragClose=onDragClose,
        onDismissRequest = {
            onDismissRequest?.invoke()
        },
        onCloseClicked = {
            coroutineScope.launch {
                if (sheetState.isVisible) {
                    sheetState.hide()
                    onCloseClick?.invoke()
                } else {
                    sheetState.show()
                }
            }
        }) {
        PromptBody(
            img = img,
            title = title,
            message = message,
            content = content,
            positiveButton = positiveButton,
            negativeButton = negativeButton
        )
    }
}



@Composable
fun <T> PromptBody(
    modifier: Modifier = Modifier,
    img: T? = null,
    title: String? = null,
    content: @Composable (() -> Unit)? = null,
    message: String? = null,
    positiveButton: @Composable (() -> Unit)? = null,
    negativeButton: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .padding(vertical = 16.sdp, horizontal = 8.sdp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        img?.let {
            ResourceImage(image = it, modifier = Modifier.size(45.sdp))
        }
        if (!title.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(12.sdp))
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        }

        if (!message.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(15.sdp))
            Text(
                text = message,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        content.let {
            content?.invoke()
        }
        Spacer(modifier = Modifier.height(25.sdp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            positiveButton?.invoke()
            negativeButton?.invoke()
        }
        Spacer(modifier = Modifier.height(20.sdp))
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowSuccess(
    modifier: Modifier = Modifier,
    title: String? = null,
    message: String? = null,
    cancelable: Boolean = true,
    onDragClose: Boolean = true,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    img: Int ?= R.drawable.ic_success,
    buttonText: String = "Okay",
    buttonClick: () -> Unit={},
    onDismiss:()->Unit={}
) {
    var sheetVisible by remember { mutableStateOf(true) }
    val sheetState: SheetState =  SheetState(
        skipPartiallyExpanded = true,
        density = LocalDensity.current,
        initialValue = SheetValue.Expanded
    )
    if (sheetVisible) {
        ShowPrompt(
            modifier = modifier,
            title = title,
            sheetState=sheetState,
            onDragClose=onDragClose,
            message = message,
            img = img,
            onDismissRequest = {
                sheetVisible=false
                onDismiss()
                },
            onCloseClick = {
                sheetVisible=false
                onDismiss()
            },
            cancelable = cancelable,
            positiveButton = {
                if (buttonText.isNotBlank()){
                    Box(modifier = Modifier.padding(horizontal = 30.sdp)) {
                        TextButton(text = buttonText, onClick = {
                            coroutineScope.launch {
                                sheetState.hide()
                            }
                            buttonClick()
                            onDismiss()
                        })
                    }
                }

            }
        )
    }
}






@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowError(
    modifier: Modifier = Modifier,
    title: String? = null,
    message: String? = null,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    img: Int? = R.drawable.ic_error,
    buttonText: String = "Okay",
    buttonClick: () -> Unit={},
    onDragClose: Boolean = true,
    cancelable: Boolean = true,
    onDismiss: () -> Unit={}
) {
    var sheetVisible by remember { mutableStateOf(true) }
    val sheetState: SheetState =  SheetState(
        skipPartiallyExpanded = true,
        density = LocalDensity.current,
        initialValue = SheetValue.Expanded
    )
    if (sheetVisible) {
        ShowPrompt(
            modifier = modifier,
            title = title,
            message = message,
            img = img,
            onDragClose=onDragClose,
            sheetState = sheetState,
            cancelable = cancelable,
            onCloseClick = {
                sheetVisible=false
                onDismiss()
            },
            onDismissRequest = {
                sheetVisible=false
                onDismiss()
            },
            positiveButton = {
                Box(modifier = Modifier.padding(horizontal = 30.sdp)) {
                    TextButton(text = buttonText, onClick = {
                            coroutineScope.launch {
                                sheetState.hide()
                            }
                        buttonClick()
                        onDismiss()
                    })
                }
            },
            /*negativeButton = {
                Box(modifier = Modifier.padding(horizontal = 30.sdp)) {
                    TextButton(text = "Dismiss", onClick = {
                        onDismiss()
                        sheetVisible=false
                    })
                }
            }*/
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowConfirmation(
    modifier: Modifier = Modifier,
    title: String? = null,
    message: String? = null,
    img: Int? = R.drawable.ic_error,
    positiveButtonText: String = "Yes",
    negativeButtonText: String = "No",
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    positiveButtonClick: () -> Unit={},
    negativeButtonClick: () -> Unit={},
    cancelable: Boolean = true,
    onDragClose: Boolean = true,
    onDismiss: () -> Unit={}
) {
    var sheetVisible by remember { mutableStateOf(true) }
    val sheetState: SheetState =  SheetState(
        skipPartiallyExpanded = true,
        density = LocalDensity.current,
        initialValue = SheetValue.Expanded
    )
    if (sheetVisible) {
        ShowPrompt(
            modifier = modifier,
            title = title,
            message = message,
            img = img,
            sheetState = sheetState,
            cancelable = cancelable,
            onDragClose=onDragClose,
            onCloseClick = {
                sheetVisible=false
                onDismiss()
            },
            onDismissRequest = {
                sheetVisible=false
                onDismiss()
            },
            positiveButton = {
                Row(Modifier.fillMaxWidth()) {
                    BaseButton(
                        modifier = Modifier.weight(1f),
                        primary = true,
                        enabled = true,
                        borderColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.background,
                        content = {
                            LabelLargeText(text =negativeButtonText, fontWeight = FontWeight.Medium)
                        },
                        onClick = {
                            coroutineScope.launch {
                                sheetState.hide()
                            }
                            negativeButtonClick()
                            onDismiss()
                        }
                    )
                    SpacerWidth(width = 10.sdp)
                    TextButton(
                        modifier = Modifier.weight(1f),
                        text = positiveButtonText, onClick = {
                            coroutineScope.launch {
                                sheetState.hide()
                            }
                        positiveButtonClick()
                        onDismiss()
                    })
                }
                /*Box(modifier = Modifier.padding(horizontal = 30.sdp)) {
                    TextButton(text = buttonText, onClick = {
                        sheetVisible=false
                        buttonClick()
                        onDismiss()
                    })
                }*/
            },
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BaseModalSheet(
    modifier: Modifier = Modifier,
    isCancelable:Boolean=true,
    onDragClose:Boolean=true,
    scope: CoroutineScope = rememberCoroutineScope(),
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismissed: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    //Issue fixed reported by qa sheet bottom padding
    ModalBottomSheet(
        modifier = modifier.padding(
            top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
        ).windowInsetsPadding(WindowInsets.ime),
        sheetState = sheetState,
        dragHandle = {},
        containerColor = MaterialTheme.colorScheme.surface,
        content = {
            content()
        },
        onDismissRequest = {
            if (onDragClose) {
                onDismissed?.invoke()
            }else{
                scope.launch {
                    sheetState.show()
                }
            }
        })
}
@Composable
fun Empty(modifier: Modifier = Modifier) {
    Box(modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseSheet(
    modifier: Modifier = Modifier,
    scope: CoroutineScope = rememberCoroutineScope(),
    canClose: Boolean = true,
    onDragClose: Boolean = true,
    sheetVisible: Boolean = true,
    skipPartiallyExpanded: Boolean = false,
    onClosed: (() -> Unit)? = null,
    onDismissed: (() -> Unit)? = null,
    title: @Composable RowScope.() -> Unit = { Empty() },
    content: @Composable ColumnScope.() -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)

    if (!sheetVisible) {
        return
    }
    BaseModalSheet(
        modifier = modifier,
        scope = scope,
        isCancelable=canClose,
        onDragClose=onDragClose,
        sheetState = sheetState,
        onDismissed = onDismissed
    ) {
        Column(
            modifier = Modifier
                .padding(16.sdp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                title()
                if (canClose) {
                    Icon(
                        Icons.Outlined.Close,
                        modifier = Modifier
                            .safeClickable {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        onClosed?.invoke()
                                    }
                                }
                            },
                        tint = MaterialTheme.colorScheme.error,
                        contentDescription = ""
                    )
                }
            }
            content()
        }
    }
}


@Composable
fun CloseWrapper(
    modifier: Modifier = Modifier,
    canClose: Boolean = true,
    onCloseClicked: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(vertical = 16.sdp, horizontal = 8.sdp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (canClose) {
            Icon(
                Icons.Outlined.Close, modifier = Modifier
                    .align(Alignment.End)
                    .safeClickable {
                        onCloseClicked?.invoke()
                    }, tint = MaterialTheme.colorScheme.error, contentDescription = ""
            )
        }
        content()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewMPINDialog() {
    AppTheme {
        val sheetState: SheetState =  SheetState(
            skipPartiallyExpanded = true,
            density = LocalDensity.current,
            initialValue = SheetValue.Expanded
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewErrorDialog() {
    AppTheme {
        ShowError(message = "This is the Error message"){

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewSuccessDialog() {
    AppTheme {
        val sheetState: SheetState =  SheetState(
            skipPartiallyExpanded = true,
            density = LocalDensity.current,
            initialValue = SheetValue.Expanded
        )
        ShowSuccess( message = "Success"){

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewConfirmationDialog() {
    AppTheme {
        val sheetState: SheetState =  SheetState(
            skipPartiallyExpanded = true,
            density = LocalDensity.current,
            initialValue = SheetValue.Expanded
        )
        ShowConfirmation( message = "Confirmation"){

        }
    }
}

