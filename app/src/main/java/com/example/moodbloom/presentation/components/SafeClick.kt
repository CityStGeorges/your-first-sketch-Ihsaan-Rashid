package com.example.moodbloom.presentation.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role


/**
 * @author: Ihsaan Rashid
 * A custom modifier function that adds a click listener with optional ripple effect and
 * support for accessibility roles.
 *
 * @param enabled Boolean value indicating whether the click is enabled. If false, the click
 *        listener is disabled. Default is true.
 * @param rippleEnabled Boolean value to enable or disable ripple effect on click. If false, no ripple
 *        effect is shown. Default is false.
 * @param onClickLabel Optional accessibility label for the click action. It provides a description
 *        for accessibility services like screen readers. Default is null.
 * @param role Optional role describing the purpose of the clickable element for accessibility.
 *        It helps to describe the type of user interface element (like button, checkbox, etc.).
 *        Default is null.
 * @param onClick Lambda function to be invoked when the element is clicked.
 */
fun Modifier.safeClickable(
    enabled: Boolean = true,
    rippleEnabled: Boolean = false,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }
    Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = { multipleEventsCutter.processEvent { onClick() } },
        role = role,
        indication=if(rippleEnabled){LocalIndication.current}else{null},
        interactionSource = remember { MutableInteractionSource() }
    )
}


@Composable
fun safeOnClick(
    enabled: Boolean = true,
    onClick: () -> Unit
): () -> Unit {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }
    return {
        if (enabled) {
            multipleEventsCutter.processEvent { onClick() }
        }
    }
}



internal interface MultipleEventsCutter {
    fun processEvent(event: () -> Unit)

    companion object
}

internal fun MultipleEventsCutter.Companion.get(): MultipleEventsCutter =
    MultipleEventsCutterImpl()

private class MultipleEventsCutterImpl : MultipleEventsCutter {
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0

    override fun processEvent(event: () -> Unit) {
        if (now - lastEventTimeMs >= 500L) {
            event.invoke()
        }
        lastEventTimeMs = now
    }
}