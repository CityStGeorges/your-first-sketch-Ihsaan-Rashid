package com.example.moodbloom.extension

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.Dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.moodbloom.presentation.components.hpr
import com.example.moodbloom.presentation.components.sdp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun SpacerHeight(
    height: Dp,
    modifier: Modifier = Modifier
) {
    Spacer(modifier = modifier.height(height))
}

@Composable
fun SpacerWidth(
    width: Dp,
    modifier: Modifier = Modifier
) {
    Spacer(modifier = modifier.width(width))
}

@Composable
fun RowScope.SpacerWeight(
    weight: Float,
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .weight(weight)

    )
}
@Composable
fun ColumnScope.SpacerWeight(
    weight: Float,
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .weight(weight)

    )
}
@Composable
fun SpaceDefault() {
    Spacer(modifier = Modifier.height(15.sdp))
}
@Composable
fun SpaceBetweenField(
    modifier:Modifier = Modifier,
    height: Dp = 1.hpr
) {
    Spacer(modifier = modifier.height(height))
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

/**
 * Extension function to apply ripple effect on any composable
 */


// Convert a date string to milliseconds
fun dateToMillis(dateString: String?,dateFormat: String?="dd MMMM, yyyy"): Long? {
    val dateFormator = SimpleDateFormat(dateFormat, Locale.getDefault())
    return try {
        dateString?.let {
            val date = dateFormator.parse(it)
            date?.time
        }
    } catch (e: Exception) {
        null
    }
}

// Convert milliseconds to formatted date string
fun millisToDate(millis: Long, dateFormat: String?="dd MMMM, yyyy"): String {
    val dateFormator = SimpleDateFormat(dateFormat, Locale.getDefault())
    val date = Date(millis)
    return dateFormator.format(date)
}


//TODO : Will be moved to the DateUtil Class
fun getFormattedDate(dateInMillis: Long?, format: String): String {
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return if (dateInMillis != null) {
        formatter.format(Date(dateInMillis))
    } else {
        ""
    }
}



fun getPreviousMonthDate(currentDateInMillis: Long, count: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = currentDateInMillis
    calendar.add(Calendar.MONTH, count)
    return calendar.timeInMillis
}

// Modifier extension function to hide keyboard
fun Modifier.hideKeyboardOnCondition(shouldHide: Boolean): Modifier = this.then(
    Modifier.composed {
        val keyboardController = LocalSoftwareKeyboardController.current
        if (shouldHide) {
            keyboardController?.hide()
        }
        this
    }
)

fun Context.hideKeyboard() {
    val imm = getSystemService(InputMethodManager::class.java)
    val view = (this as? Activity)?.currentFocus ?: View(this)
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}



fun CharSequence?.isNotNullOrBlank(): Boolean {
    return !this.isNullOrBlank()
}


// Generic extension function to convert any object to JSON string
inline fun <reified T> T.toJson(): String {
    return Gson().toJson(this)
}

// Extension function to deserialize a JSON string to a specified type
// Extension function for deserialization
inline fun <reified T> String.fromJson(): T {
    return Gson().fromJson(this, object : TypeToken<T>() {}.type)
}

// Extension function to convert pixels to dp
fun Int.pxToDp(density: Float): Int {
    return (this / density).roundToInt()
}

fun String.isValidEmail(): Boolean {
    return this.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}





fun Char.isDigit(): Boolean = this in '0'..'9'
fun String.isDigit(): Boolean = this.all { it.isDigit() }


fun Char.isLetterOrDigit(): Boolean = this.isLetter() || this.isDigit()
fun String.isLetterOrDigit(): Boolean = this.all { it.isLetterOrDigit() }


fun Char.isLetterOrDigitWithSpace(): Boolean = this.isLetter() || this.isDigit() || this== ' '
fun String.isLetterOrDigitWithSpace(): Boolean = this.all { it.isLetterOrDigitWithSpace() }



fun Char.isLetter(): Boolean = this in 'A'..'Z' || this in 'a'..'z'
fun String.isLetter(): Boolean = this.all { it.isLetter() }


fun Char.isLetterWitSpace(): Boolean = this in 'A'..'Z' || this in 'a'..'z' || this == ' '
fun String.isLetterWitSpace(): Boolean = this.all { it.isLetterWitSpace() }


fun Char.isSpecialCharacter(): Boolean = !this.isLetterOrDigit()
fun String.isSpecialCharacter(): Boolean = this.all { it.isSpecialCharacter() }


fun String.getDrawableResourceId(context: Context): Int? {
    val resourceName = this.substringAfterLast(".").takeIf { it.isNotEmpty() } ?: return null // Extract the drawable name
    try {
        val packageName = context.packageName

        return context.resources.getIdentifier(resourceName, "drawable", packageName).takeIf { it != 0 }
    } catch (e: Exception) {
        return null
    }
}

fun String.getDrawable(context: Context): Drawable? {
    try {
        val resourceId = this.getDrawableResourceId(context) ?: return null
        return ContextCompat.getDrawable(context, resourceId)
    } catch (e: Exception) {
        return null
    }
}


fun Int.formatTime(): String {
    val minutes = this / 60
    val remainingSeconds = this % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}



