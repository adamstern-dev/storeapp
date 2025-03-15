package com.drund.storedemo.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Common text styles used throughout the application
 */
object AppTextStyles {
    val titleLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = DarkBlue,
        letterSpacing = 0.5.sp
    )

    val bodyMedium = TextStyle(
        fontSize = 12.sp,
        color = Black,
        letterSpacing = 0.25.sp
    )

    val priceLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = PrimaryBlue,
        letterSpacing = 0.5.sp
    )
}

/**
 * Common dimensions used throughout the application
 */
object AppDimensions {
    // Padding
    val paddingSmall = 8.dp
    val paddingMedium = 12.dp
    val paddingLarge = 16.dp

    // Spacing
    val spacingSmall = 8.dp
    val spacingMedium = 12.dp
    val spacingLarge = 16.dp

    // Sizes
    val productImageHeight = 200.dp
    val cornerRadiusSmall = 4.dp

    // Card
    val cardPadding = 12.dp
} 