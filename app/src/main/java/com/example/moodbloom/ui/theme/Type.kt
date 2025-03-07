package com.example.moodbloom.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.moodbloom.presentation.components.textSdp


import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.example.moodbloom.R
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontName = GoogleFont("Lato")

val fontFamilyOutfit = FontFamily(
    Font(googleFont = fontName, fontProvider = provider)
)
/*
val fontFamily= FontFamily(
    Font(R.font.font_family, FontWeight.Normal),
    Font(R.font.font_family_bold, FontWeight.Bold),
    Font(R.font.font_family_light, FontWeight.Light),
    Font(R.font.font_family_regular, FontWeight.Medium),
)*/



val Typography: Typography
    @Composable
    get() = Typography(
        displayLarge = TextStyle(
            fontFamily = fontFamilyOutfit,
            fontWeight = FontWeight.Normal,
            fontSize = 29.textSdp,
            lineHeight = 31.textSdp
        ),
        displayMedium = TextStyle(
            fontFamily = fontFamilyOutfit,
            fontWeight = FontWeight.Normal,
            fontSize = 26.textSdp,
            lineHeight = 31.textSdp
        ),
        displaySmall = TextStyle(
            fontFamily = fontFamilyOutfit,
            fontWeight = FontWeight.Normal,
            fontSize = 21.textSdp,
            lineHeight = 25.textSdp
        ),
        headlineLarge = TextStyle(
            fontFamily = fontFamilyOutfit,
            fontWeight = FontWeight.SemiBold,
            fontSize = 23.textSdp,
            lineHeight = 23.textSdp
        ),
        headlineMedium = TextStyle(
            fontFamily = fontFamilyOutfit,
            fontWeight = FontWeight.Bold,
            fontSize = 20.textSdp,
            lineHeight = 25.textSdp
        ),
        headlineSmall = TextStyle(
            fontFamily = fontFamilyOutfit,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.textSdp,
            lineHeight = 25.textSdp
        ),
        titleLarge = TextStyle(
            fontFamily = fontFamilyOutfit,
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.textSdp,
            lineHeight = 24.textSdp
        ),
        titleMedium = TextStyle(
            fontFamily = fontFamilyOutfit,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primaryContainer,
            fontSize = 15.textSdp,
            lineHeight = 27.textSdp
        ),
        titleSmall = TextStyle(
            fontFamily = fontFamilyOutfit,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.textSdp,
            lineHeight = 17.textSdp
        ),
        labelLarge = TextStyle(
            fontFamily = fontFamilyOutfit,
            fontWeight = FontWeight.Bold,
            fontSize = 17.textSdp,
            lineHeight = 19.textSdp,
        ),
        labelMedium = TextStyle(
            fontFamily = fontFamilyOutfit,
            fontWeight = FontWeight.Medium,
            fontSize = 15.textSdp,
            lineHeight = 19.textSdp,
        ),
        labelSmall = TextStyle(
            fontFamily = fontFamilyOutfit,
            fontWeight = FontWeight.Medium,
            fontSize = 14.textSdp,
            lineHeight = 17.textSdp
        ),
        bodyLarge = TextStyle(
            fontFamily =fontFamilyOutfit,
            fontWeight = FontWeight.Normal,
            fontSize = 15.textSdp,
            lineHeight = 21.textSdp,
        ),
        bodyMedium = TextStyle(
            fontFamily =fontFamilyOutfit,
            fontWeight = FontWeight.Light,
            fontSize = 14.textSdp,
            lineHeight = 15.textSdp,
            letterSpacing = 0.6.sp
        ),
        bodySmall = TextStyle(
            fontFamily =fontFamilyOutfit,
            fontWeight = FontWeight.Normal,
            fontSize = 13.textSdp,
            lineHeight = 14.textSdp,
            letterSpacing = 0.5.sp
        ),
    )