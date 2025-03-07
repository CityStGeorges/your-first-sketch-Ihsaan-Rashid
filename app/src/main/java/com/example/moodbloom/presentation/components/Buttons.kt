package com.example.moodbloom.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.moodbloom.R
import com.example.moodbloom.extension.SpacerWidth
import com.example.moodbloom.ui.theme.AppTheme
import com.example.moodbloom.ui.theme.md_theme_light_disable
import com.example.moodbloom.ui.theme.md_theme_light_disable_outline
import com.example.moodbloom.ui.theme.md_theme_light_onDisable
import com.example.moodbloom.ui.typo.LabelLargeText


/**
 * Description:
 * This file contains the Button UI components, their layout, behavior, and
 * interaction logic used in the this application.
 *
 **/

@Composable
fun BaseButton(
    modifier: Modifier,
    primary: Boolean = true,
    enabled: Boolean = true,
    onClick: () -> Unit,
    changeColorsOnDisable:Boolean= true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    shape : Shape = MaterialTheme.shapes.large,
    contentColor: Color =  if(primary){MaterialTheme.colorScheme.onPrimary}else{MaterialTheme.colorScheme.onSecondary},
    containerColor: Color = if(changeColorsOnDisable && !enabled){md_theme_light_disable} else if(primary){MaterialTheme.colorScheme.primary}else{MaterialTheme.colorScheme.secondary},
    borderColor: Color = if(changeColorsOnDisable && !enabled){md_theme_light_disable_outline} else {Color.Unspecified},
    content: @Composable () -> Unit,
) {
        Button(
            modifier = modifier.height(50.dp),
            onClick = safeOnClick{onClick()},
            enabled = enabled,
            contentPadding = contentPadding,
            colors = ButtonDefaults.outlinedButtonColors().copy(
                contentColor = contentColor,
                containerColor = containerColor,
                disabledContentColor = if(changeColorsOnDisable && !enabled){md_theme_light_onDisable}else{ButtonDefaults.outlinedButtonColors().disabledContainerColor},
                disabledContainerColor = if(changeColorsOnDisable && !enabled){md_theme_light_disable}else{ButtonDefaults.outlinedButtonColors().disabledContainerColor}
            ),
            border = BorderStroke(
                width = 1.dp,
                color = borderColor ,
            ),
            shape = shape
        ) {
            content()
        }

}

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    text: String = "Next",
    enabled: Boolean = true,
    primary: Boolean = true,
    shape : Shape = MaterialTheme.shapes.large,    changeColorsOnDisable: Boolean = true,
    textColor: Color = if(primary){MaterialTheme.colorScheme.onPrimary}else{MaterialTheme.colorScheme.onSecondary},
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onClick: () -> Unit
) {
    BaseButton(
        modifier = modifier.fillMaxWidth(),
        primary = primary,
        enabled = enabled,
        shape=shape,
        changeColorsOnDisable=changeColorsOnDisable,
        contentPadding=contentPadding,
        content = {
            if (enabled){
                LabelLargeText(text = text,fontWeight = FontWeight.Medium, overrideColor = textColor)
            }else if(changeColorsOnDisable) {
                LabelLargeText(text = text, fontWeight = FontWeight.Medium,overrideColor = md_theme_light_onDisable)
            }else {
                LabelLargeText(text = text,fontWeight = FontWeight.Medium, overrideColor = textColor)
            }
        },
        onClick = onClick
    )
}

@Composable
fun LoginWithGoogleButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    primary: Boolean = false,
    text: String = "Google",
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    changeColorsOnDisable: Boolean = true,
    iconPainter:Int = R.drawable.ic_google,
    textColor: Color = if(primary){MaterialTheme.colorScheme.onPrimary}else{MaterialTheme.colorScheme.onPrimaryContainer},
    onClick: () -> Unit,
) {

    BaseButton(
        enabled = enabled,
        primary = primary,
        modifier = modifier,
        containerColor= Color.White,
        borderColor = MaterialTheme.colorScheme.outline,
        shape = MaterialTheme.shapes.extraLarge,
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = horizontalArrangement
            ) {
                if (horizontalArrangement == Arrangement.Center) {

                    ResourceImage(
                        image = iconPainter,
                        modifier = Modifier.size(18.sdp),
                    )
                    SpacerWidth(width = 10.sdp)
                    if (enabled){
                        LabelLargeText(text = text, fontWeight = FontWeight.Bold,overrideColor = textColor)
                    }else if(changeColorsOnDisable) {
                        LabelLargeText(text = text, fontWeight = FontWeight.Bold,overrideColor = md_theme_light_onDisable)
                    }else {
                        LabelLargeText(text = text, fontWeight = FontWeight.Bold,overrideColor = textColor)
                    }

                } else {
                    if (enabled){
                        LabelLargeText(text = text, fontWeight = FontWeight.Bold,overrideColor = textColor)
                    }else if(changeColorsOnDisable) {
                        LabelLargeText(text = text, fontWeight = FontWeight.Bold,overrideColor = md_theme_light_onDisable)
                    }else {
                        LabelLargeText(text = text, fontWeight = FontWeight.Bold,overrideColor = textColor)
                    }
                    ResourceImage(image = iconPainter, modifier = Modifier.size(18.sdp))
                }

            }

        },
        onClick = onClick
    )

}


@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    iconPainter: Painter,
    contentDescription: String = "",
    enabled: Boolean = true,
    primary: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.tertiary,
    borderColor: Color = MaterialTheme.colorScheme.tertiary,
    tintColor: Color = Color.Unspecified,
    shape : Shape = MaterialTheme.shapes.large,
    onClick: () -> Unit
) {
    BaseButton(
        modifier = modifier,
        primary = primary,
        containerColor = containerColor,
        borderColor = borderColor,
        content = {
            Icon(modifier = contentModifier, painter = iconPainter, contentDescription = contentDescription, tint = tintColor)
        },
        shape=shape,
        enabled = enabled,
        onClick = onClick
    )
}


@Composable
fun TextIconButton(
    modifier: Modifier = Modifier,
    iconPainter: Painter,
    contentDescription: String = "",
    title: String = "",
    tint: Color = LocalContentColor.current,
    primary: Boolean = true,
    enabled: Boolean = true,
    changeColorsOnDisable: Boolean = true,
    textColor: Color = if(primary){MaterialTheme.colorScheme.onPrimary}else{MaterialTheme.colorScheme.onSecondary},
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    onClick: () -> Unit,
) {
    BaseButton(
        enabled = enabled,
        primary = primary,
        modifier = modifier,
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = horizontalArrangement
            ) {
                if (horizontalArrangement == Arrangement.Center) {
                    if (enabled){
                        LabelLargeText(text = title, fontWeight = FontWeight.Medium, overrideColor = textColor)
                    }else if(changeColorsOnDisable) {
                        LabelLargeText(text = title,fontWeight = FontWeight.Medium, overrideColor = md_theme_light_onDisable)
                    }else {
                        LabelLargeText(text = title,fontWeight = FontWeight.Medium, overrideColor = textColor)
                    }

                    SpacerWidth(width = 10.sdp)
                    Icon(
                        painter = iconPainter,
                        contentDescription = contentDescription,
                        modifier = Modifier.padding(end = 8.dp),
                        tint = tint
                    )
                } else {
                    if (enabled){
                        LabelLargeText(text = title, fontWeight = FontWeight.Medium,overrideColor = textColor)
                    }else if(changeColorsOnDisable) {
                        LabelLargeText(text = title, fontWeight = FontWeight.Medium,overrideColor = md_theme_light_onDisable)
                    }else {
                        LabelLargeText(text = title, fontWeight = FontWeight.Medium,overrideColor = textColor)
                    }
                    Icon(painter = iconPainter, contentDescription = contentDescription)
                }

            }

        },
        onClick = onClick
    )
}

@Composable
fun TextIconPrimaryButton(
    modifier: Modifier = Modifier,
    iconPainter: Painter,
    contentDescription: String = "",
    title: String = "",
    tint: Color = LocalContentColor.current,
    enabled: Boolean = true,
    changeColorsOnDisable: Boolean = true,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    onClick: () -> Unit,
) {
    TextIconButton(
        modifier = modifier,
        iconPainter = iconPainter,
        contentDescription = contentDescription,
        title = title,
        tint = tint,
        changeColorsOnDisable=changeColorsOnDisable,
        horizontalArrangement = horizontalArrangement,
        enabled = enabled,
        onClick = onClick
    )
}

@Composable
fun TitleIconWithDescriptionCard(
    modifier: Modifier = Modifier,
    icon: @Composable() () -> Unit,
    title: @Composable() () -> Unit,
    description: @Composable() () -> Unit,
    onClick: () -> Unit,
) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        onClick = safeOnClick{onClick()},
        border = BorderStroke(1.dp, Color.Black),
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            icon()
            Column {
                title()
                description()
            }
        }
    }
}


@Composable
fun NextButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    primary: Boolean = true,
    changeColorsOnDisable: Boolean = true,
    title: String = stringResource(R.string.next),
    textColor: Color = if(primary){MaterialTheme.colorScheme.onPrimary}else{MaterialTheme.colorScheme.onSecondary},
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    iconPainter : Painter = painterResource(id = R.drawable.ic_arrow_right),
    onClick: () -> Unit,
) {
    TextIconButton(
        modifier = modifier,
        primary = primary,
        iconPainter = iconPainter,
        title = title,
        textColor=textColor,
        changeColorsOnDisable = changeColorsOnDisable,
        horizontalArrangement = horizontalArrangement,
        contentDescription = "",
        enabled = enabled,
        onClick = onClick
    )
}



@Composable
fun SmallIconButton(
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    enabled: Boolean = true,
    primary: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    borderColor: Color = Color.Unspecified,
    tintColor: Color = Color.Unspecified,
    shape : Shape = MaterialTheme.shapes.large,
    iconPainter: Painter = painterResource(id = R.mipmap.ic_launcher_round),
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        contentModifier=contentModifier,
        primary = primary,
        tintColor = tintColor,
        containerColor = containerColor,
        borderColor = borderColor,
        iconPainter = iconPainter,
        contentDescription = "",
        enabled = enabled,
        onClick = onClick,
        shape=shape
    )
}


@Composable
fun CircularButton(
    modifier: Modifier = Modifier,
    primary: Boolean = true,
    enabled: Boolean = true,
    size: Dp = 56.sdp, // Default size for the circular button
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    BaseButton(
        modifier = modifier
            .size(size)
            .clip(CircleShape),
        primary = primary,
        enabled = enabled,
        onClick = onClick,
    ) {
        content()
    }
}



@Preview
@Composable
fun PreviewButtonsCatalog() {

    AppTheme {
        Surface {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Buttons", modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )

                Text(
                    text = "Primary Button with text and icon", modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    style = TextStyle.Default.copy(color = Color.Gray)
                )
                NextButton(
                    modifier = Modifier.padding(top = 8.dp)
                ) {

                }

                Text(
                    text = "Button Disable State", modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    style = TextStyle.Default.copy(color = Color.Gray)
                )
                NextButton(
                    enabled = false,
                    modifier = Modifier.padding(top = 8.dp)
                ) {

                }
                Text(
                    text = "Secondary Button with text and icon", modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    style = TextStyle.Default.copy(color = Color.Gray)
                )
                NextButton(
                    primary = false,
                    modifier = Modifier.padding(top = 8.dp)
                ) {

                }


                Text(
                    text = "Text Buttons", modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )

                TextButton(
                    text = "Next",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {

                }

                Text(
                    text = "Small Button", modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )

                BaseButton(
                    modifier = Modifier.height(30.dp),
                    primary = true,
                    enabled = true,
                    content = {
                        Text(
                            text = "Next", style = MaterialTheme.typography.labelMedium
                        )
                    },
                    onClick = {

                    }
                )

                Text(
                    text = "Icon with Title and Description",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TitleIconWithDescriptionCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        title = { Text(text = "Title") },
                        description = { Text(text = "Descrip") },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_info),
                                contentDescription = "an"
                            )
                        }
                    ) {

                    }
                    TitleIconWithDescriptionCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        title = { Text(text = "Title2") },
                        description = { Text(text = "Descrip2") },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_info),
                                contentDescription = "an"
                            )
                        }
                    ) {

                    }
                }

                Text(
                    text = "Icon Buttons", modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    SmallIconButton {
                    }
                    SmallIconButton(
                        primary = false,
                        modifier = Modifier
                            .padding(start = 8.dp)
                    ) {

                    }
                }

            }
        }
    }
}
