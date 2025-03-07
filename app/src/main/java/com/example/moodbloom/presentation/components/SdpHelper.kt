package com.example.moodbloom.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

/**
 * This file contains extension properties and functions for defining [SDP](https://github.com/intuit/sdp)
 *
 */


// for Height in Percentage according to device screen Height ( 1% to 100% )
val Int.hpr: Dp
    @Composable
    get() {
        val screenDimensions = rememberScreenDimensions()
        val screenHeightDp = remember { screenDimensions.height }
        return (screenHeightDp * (this / 100f))
    }

val Float.hpr: Dp
    @Composable
    get() {
        val screenDimensions = rememberScreenDimensions()
        val screenHeightDp = remember { screenDimensions.height }
        return (screenHeightDp * (this / 100f))
    }

// for Width in Percentage according to device screen Width ( 1% to 100% )
val Int.wpr: Dp
    @Composable
    get() {
        val screenDimensions = rememberScreenDimensions()
        val screenWidthDp = remember { screenDimensions.width }
        return (screenWidthDp * (this / 100f))
    }
val Float.wpr: Dp
    @Composable
    get() {
        val screenDimensions = rememberScreenDimensions()
        val screenWidthDp = remember { screenDimensions.width }
        return (screenWidthDp * (this / 100f))
    }


val Int.sdp: Dp
    @Composable
    get() = this.sdpGet()



val Int.textSdp: TextUnit
    @Composable get() =  TextUnit((this*.77).toInt().sdp.value, TextUnitType.Sp)

/*val Int.textSsp: TextUnit
    @Composable get() =  TextUnit((this*.77).toInt().ssp.value, TextUnitType.Sp)*/

@Composable
private fun Int.textSsp(density: Density): TextUnit = with(density) {
    this@textSsp.sdp.toSp()
}

@Composable
private fun Int.sdpGet(): Dp {

    val id = when (this) {
        in 1..600 -> "${this}sdp"
        in (-60..-1) -> "minus${this}sdp"
        else -> return this.dp* 1.2f
    }

    val resourceField = getFieldId(id)
    return if (resourceField != 0) dimensionResource(id = resourceField) else this.dp * 1.2f

}


/**
 * Returns resource field identifier given the resource id in String format
 * @param id of resource
 */


@Composable
private fun getFieldId(id: String): Int {
    val context = LocalContext.current
    return context.resources.getIdentifier("_$id", "dimen", context.packageName)

}


data class ScreenDimensions(val height: Dp, val width: Dp)

@Composable
fun rememberScreenDimensions(): ScreenDimensions {
    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp.dp
    val width = configuration.screenWidthDp.dp
    return remember(configuration) { ScreenDimensions(height, width) }
}