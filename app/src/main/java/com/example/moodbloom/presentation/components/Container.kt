package com.example.moodbloom.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.moodbloom.ui.theme.LocalShadows
import com.example.moodbloom.ui.theme.Shadow
import com.example.moodbloom.ui.theme.md_theme_light_disable_outline

@Composable
fun CardContainer(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    shadow: Shadow = LocalShadows.current.none,
    contentAlignment: Alignment = Alignment.TopStart,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    containerColor: Color = MaterialTheme.colorScheme.background,
    content: @Composable BoxScope.() -> Unit,
) {
    BaseCardContainer(
        contentAlignment=contentAlignment,
        modifier = modifier,
        style = ContainerStyle.Outlined(
            enabledBorderColor = borderColor,
            disabledBorderColor = MaterialTheme.colorScheme.primary,
            enabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.surfaceVariant,
            enabledContainerColor = containerColor,
            shape = shape,
            shadow = shadow,
        ), content = content
    )
}


@Composable
fun BaseCardContainer(
    modifier: Modifier,
    enabled: Boolean = true,
    style: ContainerStyle,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit,
) {
    Surface(
        modifier = modifier.shadow(
            elevation = style.shadow.elevation,
            shape = style.shape,
            ambientColor = style.shadow.color
        ),
        shape = style.shape,
        border = BorderStroke(
            width = if (enabled) style.focusedBorderWidth else style.unfocusedBorderWidth,
            color = if (enabled) style.enabledBorderColor else style.disabledBorderColor
        ),
        color = if (enabled) style.enabledContainerColor else style.disabledContainerColor,
        contentColor = if (enabled) style.enabledContentColor else style.disabledContentColor
    ) {
        Box(contentAlignment=contentAlignment) {
            content()
        }
    }
}


/**
 * `ContainerStyle` is a sealed class representing different styles for displaying containers
 * This class provides a flexible way to customize the appearance of containers in this Application.
 *
 * @property enabledContainerColor The background color of the digit container.
 * @property focusedBorderColor The border color when the digit container is focused.
 * @property unfocusedBorderColor The border color when the digit container is not focused.
 * @property focusedBorderWidth The border width when the digit container is focused.
 * @property unfocusedBorderWidth The border width when the digit container is not focused.
 * @property errorColor The color used to indicate an error state in the digit container.
 */
sealed class ContainerStyle(
    open val containerColor: Color = Color.Unspecified,
    open val enabledContainerColor: Color = Color.Unspecified,
    open val disabledContainerColor: Color = Color.Unspecified,
    open val focusedBorderColor: Color = Color.Red,
    open val unfocusedBorderColor: Color = Color.Green,
    open val enabledBorderColor: Color = Color.Unspecified,
    open val disabledBorderColor: Color = Color.Unspecified,
    open val enabledContentColor: Color = Color.Unspecified,
    open val disabledContentColor: Color = Color.Unspecified,
    open val focusedBorderWidth: Dp = 2.dp,
    open val unfocusedBorderWidth: Dp = 2.dp,
    open val errorColor: Color = Color.Unspecified,
    open val shape: Shape = RoundedCornerShape(8.dp),
    open val shadow: Shadow = Shadow(elevation = 1.dp, color = Color.Black)
) {

    /**
     * Returns the appropriate border width based on the focus state of the digit container.
     *
     * @param focused Whether the digit container is currently focused.
     * @return The border width to be used for the digit container.
     */
    fun borderWidth(focused: Boolean): Dp {
        return if (focused) focusedBorderWidth else unfocusedBorderWidth
    }

    /**
     * Returns the appropriate border color based on the focus and error states of the digit container.
     *
     * @param focused Whether the digit container is currently focused.
     * @param error Whether the digit container is in an error state.
     * @return The border color to be used for the digit container.
     */
    fun borderColor(focused: Boolean, error: Boolean): Color {
        return when {
            error -> errorColor
            focused -> focusedBorderColor
            else -> unfocusedBorderColor
        }
    }

    /**
     * Represents an outlined style for the digit container of our [PinInputField].
     *
     * @property size The size of the digit container.
     * @property shape The shape of the digit container.
     */
    data class Outlined(
        override val shadow: Shadow = Shadow(elevation = 1.dp, color = Color.Black),
        override val shape: Shape = RoundedCornerShape(12.dp),
        override val containerColor: Color = Color.Unspecified,
        override val enabledContainerColor: Color = Color.Unspecified,
        override val focusedBorderColor: Color = Color.Unspecified,
        override val unfocusedBorderColor: Color = Color.Unspecified,
        override val enabledBorderColor: Color = Color.Unspecified,
        override val disabledBorderColor: Color = Color.Unspecified,
        override val enabledContentColor: Color = Color.Unspecified,
        override val disabledContentColor: Color = Color.Unspecified,
        override val focusedBorderWidth: Dp = 1.0.dp,
        override val unfocusedBorderWidth: Dp = 0.8.dp,
        override val errorColor: Color = Color.Unspecified,
    ) : ContainerStyle(
        enabledContainerColor = enabledContainerColor,
        focusedBorderColor = focusedBorderColor,
        unfocusedBorderColor = unfocusedBorderColor,
        unfocusedBorderWidth = unfocusedBorderWidth,
        focusedBorderWidth = focusedBorderWidth,
        errorColor = errorColor
    )


    data class Filled(
        override val shadow: Shadow = Shadow(elevation = 1.dp, color = Color.Black),
        override val shape: Shape = RoundedCornerShape(12.dp),
        override val enabledContainerColor: Color = Color.Unspecified,
        override val disabledContainerColor: Color = Color.Unspecified,
        override val focusedBorderColor: Color = Color.Unspecified,
        override val unfocusedBorderColor: Color = Color.Unspecified,
        override val enabledBorderColor: Color = Color.Unspecified,
        override val disabledBorderColor: Color = Color.Unspecified,
        override val enabledContentColor: Color = Color.Unspecified,
        override val disabledContentColor: Color = Color.Unspecified,
        override val focusedBorderWidth: Dp = 1.0.dp,
        override val unfocusedBorderWidth: Dp = 0.8.dp,
        override val errorColor: Color = Color.Unspecified,
    ) : ContainerStyle(
        enabledContainerColor = enabledContainerColor,
        focusedBorderColor = focusedBorderColor,
        unfocusedBorderColor = unfocusedBorderColor,
        unfocusedBorderWidth = unfocusedBorderWidth,
        focusedBorderWidth = focusedBorderWidth,
        errorColor = errorColor
    )
}





@Composable
fun BaseCardProgressContainer(
    modifier: Modifier = Modifier,
    isCompleted: Boolean = true,
    progress: Float = 0.5f, // 50% progress
    progressedBorderColor: Color = MaterialTheme.colorScheme.primary,
    unProgressedBorderColor: Color = md_theme_light_disable_outline,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentAlignment: Alignment = Alignment.TopStart,
    shape: Shape = MaterialTheme.shapes.large,
    cornerRadius:Float = 10f,
    borderWidth: Float = 8f,
    content: @Composable BoxScope.() -> Unit,
) {
    Surface(
        modifier = modifier
            .drawBehind {
                // Border properties

                val strokeWidth = borderWidth
                val halfStroke = strokeWidth / 2
                val size = Size(size.width - strokeWidth, size.height - strokeWidth)

                drawRoundRect(
                    color = unProgressedBorderColor,
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                    style = Stroke(width = strokeWidth)
                )

                // Draw progress border (Blue)
                val sweepAngle = 360 * progress
                drawArc(
                    color = progressedBorderColor,
                    startAngle = -90f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    size = size,
                    topLeft = Offset(halfStroke, halfStroke),
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }
            .padding(borderWidth.dp), // Avoids content overlap
        shape = RoundedCornerShape(cornerRadius.dp),
        color = containerColor, // Background color
        contentColor = Color.Unspecified,
    ) {
        Box(
            contentAlignment = contentAlignment,
            modifier = Modifier.padding(4.dp)
        ) {
            content()
        }
    }
}




@Composable
fun ProgressBorderContainer(
    modifier: Modifier = Modifier,
    isCompleted:Boolean=false,
    progress: Float, // Progress from 0.0 to 1.0
    borderWidth: Float = 8f,
    cornerRadius: Float = 10f,
    progressColor: Color = MaterialTheme.colorScheme.primary,
    unprogressColor: Color = md_theme_light_disable_outline,
    content: @Composable BoxScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius.dp))
            .drawBehind {
                val strokeWidth = borderWidth
                val halfStroke = strokeWidth / 2
                val size = Size(size.width - strokeWidth, size.height - strokeWidth)

                // Draw unprogressed border (Gray)
                drawRoundRect(
                    color = unprogressColor,
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                    style = Stroke(width = strokeWidth)
                )

                // Draw progress border (Blue)
                val sweepAngle = 360 * progress
                drawArc(
                    color = progressColor,
                    startAngle = -90f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    size = size,
                    topLeft = Offset(halfStroke, halfStroke),
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }
            .padding(borderWidth.dp), // Avoids content overlap
        shape = RoundedCornerShape(cornerRadius.dp),
        color = Color.White, // Background color
        contentColor = Color.Black
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            content()
        }
    }
}

