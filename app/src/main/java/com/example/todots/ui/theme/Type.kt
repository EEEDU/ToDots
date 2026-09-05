package com.example.todots.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.todots.R

val alegreya = FontFamily(Font(R.font.alegreya))
val alegreya_sans = FontFamily(Font(R.font.alegreya_sans))

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = alegreya,
        fontWeight = FontWeight.Normal,
        fontSize = 21.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = alegreya,
        fontWeight = FontWeight.Bold,
        fontSize = 21.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = alegreya,
        fontWeight = FontWeight.ExtraBold,   // por ejemplo, más peso
        fontSize = 42.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.sp
    )
    /* Other default text styles to override
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)