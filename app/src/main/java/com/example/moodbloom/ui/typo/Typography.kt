package com.example.moodbloom.ui.typo

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.example.moodbloom.presentation.components.PreviewWrapper

/**
 * Text parent
 *
 * Texts are designed so that styles are centrally maintained and allow
 * uniform and consistent design through out the app
 *
 * @param modifier Modifier
 * @param text Text value
 * @param textStyle [TextStyle] to apply
 */
@Composable
internal fun BaseText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    textStyle: TextStyle,
    overflow : TextOverflow = TextOverflow.Visible,
) {
    Text(
        modifier = modifier,
        text = text,
        maxLines =maxLines ,
        minLines =minLines ,
        style = textStyle,
        overflow = overflow
    )
}

/**
 * Text with `MaterialTheme.typography.displayLarge` [TextStyle].
 *
 * Largest text in the app.
 *
 *  Use for:
 *  - App logo
 *
 * @param modifier Modifier
 * @param text Text value
 * @param overrideColor text color to use, by default `MaterialTheme.colorScheme.onSurface` color is used
 * @param textAlign Alignment of text, default is [TextAlign.Start]
 *
 * @see [MaterialTheme.typography](https://m3.material.io/styles/typography/type-scale-tokens)
 */
@Composable
fun DisplayLargeText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    fontSize:TextUnit = MaterialTheme.typography.displayLarge.fontSize,
    overrideColor: Color = MaterialTheme.colorScheme.onBackground,
    fontWeight: FontWeight? = MaterialTheme.typography.displayLarge.fontWeight,
    textAlign: TextAlign = TextAlign.Start
) {
    BaseText(
        modifier = modifier,
        text = text,
        maxLines= maxLines,
        minLines=minLines,
        textStyle = MaterialTheme.typography.displayLarge.copy(
            textAlign = textAlign,
            fontWeight = fontWeight,
            color = overrideColor,
            fontSize=fontSize
        )
    )
}

/**
 * Text with `MaterialTheme.typography.displayMedium` [TextStyle].
 *
 * Uses:
 *
 * @param modifier Modifier
 * @param text Text value
 * @param overrideColor text color to use, by default `MaterialTheme.colorScheme.onSurface` color is used
 * @param textAlign Alignment of text, default is [TextAlign.Start]
 *
 * @see [MaterialTheme.typography](https://m3.material.io/styles/typography/type-scale-tokens)
 */
@Composable
fun DisplayMediumText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    fontSize:TextUnit = MaterialTheme.typography.displayMedium.fontSize,
    overrideColor: Color = MaterialTheme.colorScheme.onBackground,
    fontWeight: FontWeight? = MaterialTheme.typography.displayMedium.fontWeight,
    textAlign: TextAlign = TextAlign.Start
) {
    BaseText(
        modifier = modifier,
        text = text,
        maxLines= maxLines,
        minLines=minLines,
        textStyle = MaterialTheme.typography.displayMedium.copy(
            textAlign = textAlign,
            color = overrideColor,
            fontWeight = fontWeight,
            fontSize=fontSize
        )
    )
}

/**
 * Text with `MaterialTheme.typography.displaySmall` [TextStyle].
 *
 * Uses:
 *
 * @param modifier Modifier
 * @param text Text value
 * @param overrideColor text color to use, by default `MaterialTheme.colorScheme.onSurface` color is used
 * @param textAlign Alignment of text, default is [TextAlign.Start]
 *
 * @see [MaterialTheme.typography](https://m3.material.io/styles/typography/type-scale-tokens)
 */
@Composable
fun DisplaySmallText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    fontSize: TextUnit = MaterialTheme.typography.displaySmall.fontSize,
    fontWeight: FontWeight? =  MaterialTheme.typography.displaySmall.fontWeight,
    overrideColor: Color = MaterialTheme.colorScheme.onBackground,
    textAlign: TextAlign = TextAlign.Start
) {
    BaseText(
        modifier = modifier,
        text = text,
        maxLines= maxLines,
        minLines=minLines,
        textStyle = MaterialTheme.typography.displaySmall.copy(
            textAlign = textAlign,
            fontWeight = fontWeight,
            color = overrideColor,
            fontSize=fontSize
        )

    )
}

/**
 * Text with `MaterialTheme.typography.headlineLarge` [TextStyle].
 *
 * Largest headline, reserved for short important text or numerals.
 *
 * Uses:
 * - Large Amount fields in receipt and review
 *
 * @param modifier Modifier
 * @param text Text value
 * @param overrideColor text color to use, by default `MaterialTheme.colorScheme.onSurface` color is used
 * @param textAlign Alignment of text, default is [TextAlign.Start]
 *
 * @see [MaterialTheme.typography](https://m3.material.io/styles/typography/type-scale-tokens)
 */
@Composable
fun HeadlineLargeText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    overrideColor: Color = MaterialTheme.colorScheme.onBackground,
    fontSize: TextUnit = MaterialTheme.typography.headlineLarge.fontSize,
    fontWeight: FontWeight? =  MaterialTheme.typography.headlineLarge.fontWeight,
    textAlign: TextAlign = TextAlign.Start
) {
    BaseText(
        modifier = modifier,
        text = text,
        maxLines= maxLines,
        minLines=minLines,
        textStyle = MaterialTheme.typography.headlineLarge.copy(
            textAlign = textAlign,
            fontWeight = fontWeight,
            color = overrideColor,
            fontSize=fontSize
        )
    )
}

/**
 * Text with `MaterialTheme.typography.headlineMedium` [TextStyle].
 *
 * Use for:
 * - Page headings e.g. Transaction Successful!
 *
 *
 * @param modifier Modifier
 * @param text Text value
 * @param overrideColor text color to use, by default `MaterialTheme.colorScheme.onSurface` color is used
 * @param textAlign Alignment of text, default is [TextAlign.Start]
 *
 * @see [MaterialTheme.typography](https://m3.material.io/styles/typography/type-scale-tokens)
 */
@Composable
fun HeadlineMediumText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    overrideColor: Color = MaterialTheme.colorScheme.onBackground,
    fontSize: TextUnit = MaterialTheme.typography.headlineMedium.fontSize,
    fontWeight: FontWeight? =  MaterialTheme.typography.headlineMedium.fontWeight,
    textAlign: TextAlign = TextAlign.Start
) {
    BaseText(
        modifier = modifier,
        text = text,
        maxLines= maxLines,
        minLines=minLines,
        textStyle = MaterialTheme.typography.headlineMedium.copy(
            textAlign = textAlign,
            fontWeight = fontWeight,
            color = overrideColor,
            fontSize=fontSize
        )
    )
}

/**
 * Text with `MaterialTheme.typography.headlineSmall` [TextStyle].
 *
 * Uses:
 * - App bar title
 * - Section headings
 *
 * @param modifier Modifier
 * @param text Text value
 * @param overrideColor text color to use, by default `MaterialTheme.colorScheme.onSurface` color is used
 * @param textAlign Alignment of text, default is [TextAlign.Start]
 *
 * @see [MaterialTheme.typography](https://m3.material.io/styles/typography/type-scale-tokens)
 */
@Composable
fun HeadlineSmallText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    overrideColor: Color = MaterialTheme.colorScheme.onBackground,
    fontSize: TextUnit = MaterialTheme.typography.headlineSmall.fontSize,
    fontWeight: FontWeight? =  MaterialTheme.typography.headlineSmall.fontWeight,
    textAlign: TextAlign = TextAlign.Start
) {
    BaseText(
        modifier = modifier,
        text = text,
        maxLines= maxLines,
        minLines=minLines,
        textStyle = MaterialTheme.typography.headlineSmall.copy(
            textAlign = textAlign,
            fontWeight = fontWeight,
            color = overrideColor,
            fontSize=fontSize
        )
    )
}

/**
 * Text with `MaterialTheme.typography.titleLarge` [TextStyle].
 *
 * Uses:
 * - Prompt title
 * -
 *
 * @param modifier Modifier
 * @param text Text value
 * @param overrideColor text color to use, by default `MaterialTheme.colorScheme.onSurface` color is used
 * @param textAlign Alignment of text, default is [TextAlign.Start]
 *
 * @see [MaterialTheme.typography](https://m3.material.io/styles/typography/type-scale-tokens)
 */
@Composable
fun TitleLargeText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    overrideColor: Color = MaterialTheme.colorScheme.onBackground,
    fontSize: TextUnit = MaterialTheme.typography.titleLarge.fontSize,
    fontWeight: FontWeight? =  MaterialTheme.typography.titleLarge.fontWeight,
    textAlign: TextAlign = TextAlign.Start
) {
    BaseText(
        modifier = modifier,
        text = text,
        maxLines= maxLines,
        minLines=minLines,
        textStyle = MaterialTheme.typography.titleLarge.copy(
            textAlign = textAlign,
            fontWeight = fontWeight,
            color = overrideColor,
            fontSize=fontSize
        )
    )
}

/**
 * Text with `MaterialTheme.typography.titleMedium` [TextStyle].
 *
 * Uses:
 * - Button titles
 * - Tabs titles
 *
 * @param modifier Modifier
 * @param text Text value
 * @param overrideColor text color to use, by default `MaterialTheme.colorScheme.onSurface` color is used
 * @param textAlign Alignment of text, default is [TextAlign.Start]
 *
 * @see [MaterialTheme.typography](https://m3.material.io/styles/typography/type-scale-tokens)
 */
@Composable
fun TitleMediumText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    overrideColor: Color = MaterialTheme.colorScheme.onBackground,
    fontSize: TextUnit = MaterialTheme.typography.titleMedium.fontSize,
    fontWeight: FontWeight? =  MaterialTheme.typography.titleMedium.fontWeight,
    textAlign: TextAlign = TextAlign.Start
) {
    BaseText(
        modifier = modifier,
        text = text,
        maxLines= maxLines,
        minLines=minLines,
        textStyle = MaterialTheme.typography.titleMedium.copy(
            textAlign = textAlign,
            fontWeight = fontWeight,
            color = overrideColor,
            fontSize = fontSize
        )
    )
}

/**
 * Text with `MaterialTheme.typography.titleSmall` [TextStyle].
 *
 *
 * Uses:
 * - Input field titles
 *
 * @param modifier Modifier
 * @param text Text value
 * @param overrideColor text color to use, by default `MaterialTheme.colorScheme.onSurface` color is used
 * @param textAlign Alignment of text, default is [TextAlign.Start]
 *
 * @see [MaterialTheme.typography](https://m3.material.io/styles/typography/type-scale-tokens)
 */
@Composable
fun TitleSmallText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    overrideColor: Color = MaterialTheme.colorScheme.onBackground,
    fontSize: TextUnit = MaterialTheme.typography.titleSmall.fontSize,
    fontWeight: FontWeight? =  MaterialTheme.typography.titleSmall.fontWeight,
    textAlign: TextAlign = TextAlign.Start
) {
    BaseText(
        modifier = modifier,
        text = text,
        maxLines= maxLines,
        minLines=minLines,
        textStyle = MaterialTheme.typography.titleSmall.copy(
            textAlign = textAlign,
            fontWeight = fontWeight,
            color = overrideColor,
            fontSize = fontSize
        )

    )
}

/**
 * Text with `MaterialTheme.typography.bodyLarge` [TextStyle].
 *
 * Uses:
 * - Input field body / Placeholder
 * - Message body
 *
 * @param modifier Modifier
 * @param text Text value
 * @param overrideColor text color to use, by default `MaterialTheme.colorScheme.onSurface` color is used
 * @param textAlign Alignment of text, default is [TextAlign.Start]
 *
 * @see [MaterialTheme.typography](https://m3.material.io/styles/typography/type-scale-tokens)
 */
@Composable
fun BodyLargeText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    overrideColor: Color = MaterialTheme.colorScheme.onBackground,
    fontSize: TextUnit = MaterialTheme.typography.bodyLarge.fontSize,
    fontWeight: FontWeight? =  MaterialTheme.typography.bodyLarge.fontWeight,
    textAlign: TextAlign = TextAlign.Start
) {
    BaseText(
        modifier = modifier,
        text = text,
        maxLines= maxLines,
        minLines=minLines,
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            textAlign = textAlign,
            fontWeight = fontWeight,
            color = overrideColor,
            fontSize = fontSize
        )

    )
}

/**
 * Text with `MaterialTheme.typography.bodyMedium` [TextStyle].
 *
 * Uses:
 * - Medium menu item text
 * -
 *
 * @param modifier Modifier
 * @param text Text value
 * @param overrideColor text color to use, by default `MaterialTheme.colorScheme.onSurface` color is used
 * @param textAlign Alignment of text, default is [TextAlign.Start]
 *
 * @see [MaterialTheme.typography](https://m3.material.io/styles/typography/type-scale-tokens)
 */
@Composable
fun BodyMediumText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    overrideColor: Color = MaterialTheme.colorScheme.onBackground,
    fontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
    fontWeight: FontWeight? =  MaterialTheme.typography.bodyMedium.fontWeight,
    textAlign: TextAlign = TextAlign.Start
) {
    BaseText(
        modifier = modifier,
        text = text,
        maxLines= maxLines,
        minLines=minLines,
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            textAlign = textAlign,
            fontWeight = fontWeight,
            color = overrideColor,
            fontSize = fontSize
        )
    )
}

/**
 * Text with `MaterialTheme.typography.bodySmall` [TextStyle].
 *
 * Use for:
 * - Small menu item text
 *
 * @param modifier Modifier
 * @param text Text value
 * @param overrideColor text color to use, by default `MaterialTheme.colorScheme.onSurface` color is used
 * @param textAlign Alignment of text, default is [TextAlign.Start]
 *
 * @see [MaterialTheme.typography](https://m3.material.io/styles/typography/type-scale-tokens)
 */
@Composable
fun BodySmallText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    overrideColor: Color = MaterialTheme.colorScheme.onBackground,
    fontSize: TextUnit = MaterialTheme.typography.bodySmall.fontSize,
    fontWeight: FontWeight? =  MaterialTheme.typography.bodySmall.fontWeight,
    textAlign: TextAlign = TextAlign.Start
) {
    BaseText(
        modifier = modifier,
        text = text,
        maxLines= maxLines,
        minLines=minLines,
        textStyle = MaterialTheme.typography.bodySmall.copy(
            textAlign = textAlign,
            fontWeight = fontWeight,
            color = overrideColor,
            fontSize = fontSize
        )

    )
}

/**
 * Text with `MaterialTheme.typography.labelLarge` [TextStyle].
 *
 * Uses:
 * - Search fields input/placeholder
 * - Check button text
 * -
 *
 * @param modifier Modifier
 * @param text Text value
 * @param overrideColor text color to use, by default `MaterialTheme.colorScheme.onSurface` color is used
 * @param textAlign Alignment of text, default is [TextAlign.Start]
 *
 * @see [MaterialTheme.typography](https://m3.material.io/styles/typography/type-scale-tokens)
 */
@Composable
fun LabelLargeText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    overrideColor: Color = MaterialTheme.colorScheme.onBackground,
    fontSize: TextUnit = MaterialTheme.typography.labelLarge.fontSize,
    fontWeight: FontWeight? =  MaterialTheme.typography.labelLarge.fontWeight,
    textAlign: TextAlign = TextAlign.Start
) {
    BaseText(
        modifier = modifier,
        text = text,
        maxLines= maxLines,
        minLines=minLines,
        textStyle = MaterialTheme.typography.labelLarge.copy(
            textAlign = textAlign,
            fontWeight = fontWeight,
            color = overrideColor,
            fontSize = fontSize
        )
    )
}

/**
 * Text with `MaterialTheme.typography.labelMedium` [TextStyle].
 *
 * Uses:
 * - Second smallest text
 * - Annotation for headline / titles
 * - Supporting text
 * - Guide text
 *
 * @param modifier Modifier
 * @param text Text value
 * @param overrideColor text color to use, by default `MaterialTheme.colorScheme.onSurface` color is used
 * @param textAlign Alignment of text, default is [TextAlign.Start]
 *
 * @see [MaterialTheme.typography](https://m3.material.io/styles/typography/type-scale-tokens)
 */
@Composable
fun LabelMediumText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    fontSize: TextUnit = MaterialTheme.typography.labelMedium.fontSize,
    fontWeight: FontWeight? =  MaterialTheme.typography.labelMedium.fontWeight,
    overrideColor: Color = MaterialTheme.colorScheme.onBackground,
    textAlign: TextAlign = TextAlign.Start
) {
    BaseText(
        modifier = modifier,
        text = text,
        maxLines= maxLines,
        minLines=minLines,
        textStyle = MaterialTheme.typography.labelMedium.copy(
            textAlign = textAlign,
            fontWeight = fontWeight,
            color = overrideColor,
            fontSize= fontSize
        )
    )
}

@Composable
fun LabelMediumTextClickable(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    overrideColor: Color = MaterialTheme.colorScheme.onBackground,
    fontSize: TextUnit = MaterialTheme.typography.labelMedium.fontSize,
    fontWeight: FontWeight? =  MaterialTheme.typography.labelMedium.fontWeight,
    textAlign: TextAlign = TextAlign.Start
) {
    BaseText(
        modifier = modifier,
        text = text,
        maxLines= maxLines,
        minLines=minLines,
        textStyle = MaterialTheme.typography.labelMedium.copy(
            textAlign = textAlign,
            fontWeight = fontWeight,
            color = overrideColor,
            fontSize = fontSize
        )
    )
}

/**
 * Text with `MaterialTheme.typography.labelSmall` [TextStyle].
 *
 * Uses:
 * - Smallest text
 * - Notification batch text
 *
 * @param modifier Modifier
 * @param text Text value
 * @param overrideColor text color to use, by default `MaterialTheme.colorScheme.onSurface` color is used
 * @param textAlign Alignment of text, default is [TextAlign.Start]
 *
 * @see [MaterialTheme.typography](https://m3.material.io/styles/typography/type-scale-tokens)
 */
@Composable
fun LabelSmallText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    overrideColor: Color = MaterialTheme.colorScheme.onBackground,
    fontSize: TextUnit = MaterialTheme.typography.labelSmall.fontSize,
    fontWeight: FontWeight? =  MaterialTheme.typography.labelSmall.fontWeight,
    textAlign: TextAlign = TextAlign.Start,
    overflow : TextOverflow = TextOverflow.Visible,
) {
    BaseText(
        modifier = modifier,
        text = text,
        maxLines= maxLines,
        minLines=minLines,
        overflow = overflow,
        textStyle = MaterialTheme.typography.labelSmall.copy(
            textAlign = textAlign,
            fontWeight = fontWeight,
            color = overrideColor,
            fontSize=fontSize
        )
    )
}


@Preview
@Composable
fun PreviewTypography() {
    PreviewWrapper(content = {
        DisplayLargeText(text = "Display LargeText")
    })
}