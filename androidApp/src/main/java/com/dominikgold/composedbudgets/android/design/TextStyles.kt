package com.dominikgold.composedbudgets.android.design

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.sp
import com.dominikgold.composedbudgets.android.R
import com.dominikgold.composedbudgets.android.design.TextStyles.CaptionBold
import com.dominikgold.composedbudgets.android.design.TextStyles.Chip
import com.dominikgold.composedbudgets.android.design.TextStyles.HeadlineLarge
import com.dominikgold.composedbudgets.android.design.TextStyles.HeadlineMedium
import com.dominikgold.composedbudgets.android.design.TextStyles.HeadlineSmall
import com.dominikgold.composedbudgets.android.design.TextStyles.Paragraph
import com.dominikgold.composedbudgets.android.design.TextStyles.ParagraphSmall
import com.dominikgold.composedbudgets.android.design.TextStyles.Subtitle

object TextStyles {

    val HeadlineLarge = TextStyle(
        fontFamily = Font(R.font.rufina_bold).toFontFamily(),
        fontSize = 28.sp,
    )

    val HeadlineMedium = TextStyle(
        fontFamily = Font(R.font.rufina_bold).toFontFamily(),
        fontSize = 24.sp,
    )

    val HeadlineSmall = TextStyle(
        fontFamily = Font(R.font.figtree).toFontFamily(),
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
    )

    val Subtitle = TextStyle(
        fontFamily = Font(R.font.figtree).toFontFamily(),
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
    )

    val SubtitleItalic = TextStyle(
        fontFamily = Font(R.font.figtree_italic).toFontFamily(),
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
    )

    val Paragraph = TextStyle(
        fontFamily = Font(R.font.figtree).toFontFamily(),
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
    )

    val ParagraphBold = TextStyle(
        fontFamily = Font(R.font.figtree).toFontFamily(),
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
    )

    val ParagraphSmall = TextStyle(
        fontFamily = Font(R.font.figtree).toFontFamily(),
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal,
    )

    val Caption = TextStyle(
        fontFamily = Font(R.font.figtree).toFontFamily(),
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
    )

    val CaptionBold = TextStyle(
        fontFamily = Font(R.font.figtree).toFontFamily(),
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
    )

    val Chip = TextStyle(
        fontFamily = Font(R.font.figtree).toFontFamily(),
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
    )
}

val Typography = Typography(
    headlineLarge = HeadlineLarge,
    headlineMedium = HeadlineMedium,
    headlineSmall = HeadlineSmall,
    bodyLarge = Paragraph,
    bodyMedium = Paragraph,
    bodySmall = ParagraphSmall,
    displayLarge = HeadlineLarge,
    displayMedium = HeadlineLarge,
    displaySmall = HeadlineMedium,
    labelLarge = Subtitle,
    labelMedium = CaptionBold,
    labelSmall = Chip,
    titleLarge = HeadlineMedium,
    titleMedium = HeadlineSmall,
    titleSmall = Subtitle,
)
