package com.dominikgold.composedbudgets.android.design

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.dominikgold.composedbudgets.android.design.Colors.DaisyWhite
import com.dominikgold.composedbudgets.android.design.Colors.FernGreen
import com.dominikgold.composedbudgets.android.design.Colors.SageGreen
import com.dominikgold.composedbudgets.android.design.Colors.Surface
import com.dominikgold.composedbudgets.android.design.Colors.SurfaceEmphasis
import com.dominikgold.composedbudgets.android.design.Colors.SystemBarsOverlay
import com.dominikgold.composedbudgets.android.design.Colors.Text
import com.dominikgold.composedbudgets.android.design.Colors.TextVeryLight
import com.dominikgold.composedbudgets.android.design.Colors.TulipRed

@Suppress("MagicNumber")
object Colors {

    val SystemBarsOverlay = Color(0x449A9A9A)

    val FernGreen = ThemeColor(
        dark = Color(0xFF5A9548),
        light = Color(0xFF5A9548),
    )

    val SageGreen = ThemeColor(
        dark = Color(0xFF51B661),
        light = Color(0xFF51B661),
    )

    val LawnGreen = ThemeColor(
        dark = Color(0xFF2FE172),
        light = Color(0xFF2FE172),
    )

    val PrimaryVariant = ThemeColor(
        dark = Color(0xFFB5DDC4),
        light = Color(0xFF34473B),
    )

    val DaisyWhite = ThemeColor(
        dark = Color(0xFFFAFEE7),
        light = Color(0xFFFAFEE7),
    )

    val Text = ThemeColor(
        dark = Color(0xFFF2FFF5),
        light = Color(0xFF121916),
    )

    val TextInverse = ThemeColor(
        light = Color(0xFFF2FFF5),
        dark = Color(0xFF121916),
    )

    val TextVeryLight = ThemeColor(
        dark = Color(0xFF464C49),
        light = Color(0xFFBFCDC7),
    )

    val TextLight = ThemeColor(
        dark = Color(0xFF8E9190),
        light = Color(0xFF8E9190),
    )

    val TextMedium = ThemeColor(
        dark = Color(0xFFBFCDC7),
        light = Color(0xFF464C49),
    )

    val TulipRed = ThemeColor(
        dark = Color(0xFFF24141),
        light = Color(0xFFF24141),
    )

    val DandelionYellow = ThemeColor(
        dark = Color(0xFFFCD33B),
        light = Color(0xFFFCD33B),
    )

    val Surface = ThemeColor(
        dark = Color(0xFF282925),
        light = Color(0xFFFFFEF8),
    )

    val SurfaceInverse = ThemeColor(
        light = Color(0xFF282925),
        dark = Color(0xFFFFFEF8),
    )

    val SurfaceEmphasis = ThemeColor(
        dark = Color(0xFF373931),
        light = Color(0xFFFBFEF1),
    )

    val Scrim = ThemeColor(
        dark = Color(0x55FFFFFF),
        light = Color(0x55000000),
    )

    val chipColors = listOf(
        TulipRed.light,
        DandelionYellow.light,
        SageGreen.light,
        LawnGreen.light,
        Color(0xFF0FB5CB),
        Color(0xFF0A8FC7),
        Color(0xFF4622D7),
        Color(0xFF670FAD),
        Color(0xFFC6169F),
        Color(0xFFA02121),
        Color(0xFFBF381B),
        Color(0xFFE6711B),
        Color(0xFFDE7E0D),
        Color(0xFFD7920E),
        Color(0xFFB9BC13),
        Color(0xFF6CAA1E),
    )
}

data class ThemeColor(val dark: Color, val light: Color) {
    @Composable
    fun dynamic() = if (LocalUseDarkTheme.current) dark else light
}

val darkColors = darkColorScheme(
    primary = FernGreen.dark,
    onPrimary = DaisyWhite.dark,
    primaryContainer = FernGreen.dark,
    onPrimaryContainer = DaisyWhite.dark,
    inversePrimary = DaisyWhite.dark,
    secondary = SageGreen.dark,
    onSecondary = DaisyWhite.dark,
    secondaryContainer = SageGreen.dark,
    onSecondaryContainer = DaisyWhite.dark,
    tertiary = SageGreen.dark,
    onTertiary = DaisyWhite.dark,
    tertiaryContainer = SageGreen.dark,
    onTertiaryContainer = DaisyWhite.dark,
    background = Surface.dark,
    onBackground = Text.dark,
    surface = Surface.dark,
    onSurface = Text.dark,
    surfaceVariant = SurfaceEmphasis.dark,
    onSurfaceVariant = Text.dark,
    surfaceTint = SurfaceEmphasis.dark,
    inverseSurface = Surface.light,
    inverseOnSurface = Text.light,
    error = TulipRed.dark,
    onError = DaisyWhite.dark,
    errorContainer = TulipRed.dark,
    onErrorContainer = DaisyWhite.dark,
    outline = TextVeryLight.dark,
    outlineVariant = TextVeryLight.dark,
    scrim = SystemBarsOverlay,
    surfaceBright = Surface.dark,
    surfaceDim = SurfaceEmphasis.dark,
    surfaceContainer = Surface.dark,
    surfaceContainerHigh = SurfaceEmphasis.dark,
    surfaceContainerHighest = SurfaceEmphasis.dark,
    surfaceContainerLow = SurfaceEmphasis.dark,
    surfaceContainerLowest = SurfaceEmphasis.dark,
)

val lightColors = lightColorScheme(
    primary = FernGreen.light,
    onPrimary = DaisyWhite.light,
    primaryContainer = FernGreen.light,
    onPrimaryContainer = DaisyWhite.light,
    inversePrimary = DaisyWhite.light,
    secondary = SageGreen.light,
    onSecondary = DaisyWhite.light,
    secondaryContainer = SageGreen.light,
    onSecondaryContainer = DaisyWhite.light,
    tertiary = SageGreen.light,
    onTertiary = DaisyWhite.light,
    tertiaryContainer = SageGreen.light,
    onTertiaryContainer = DaisyWhite.light,
    background = Surface.light,
    onBackground = Text.light,
    surface = Surface.light,
    onSurface = Text.light,
    surfaceVariant = SurfaceEmphasis.light,
    onSurfaceVariant = Text.light,
    surfaceTint = SurfaceEmphasis.light,
    inverseSurface = Surface.dark,
    inverseOnSurface = Text.dark,
    error = TulipRed.light,
    onError = DaisyWhite.light,
    errorContainer = TulipRed.light,
    onErrorContainer = DaisyWhite.light,
    outline = TextVeryLight.light,
    outlineVariant = TextVeryLight.light,
    scrim = SystemBarsOverlay,
    surfaceBright = Surface.light,
    surfaceDim = SurfaceEmphasis.light,
    surfaceContainer = Surface.light,
    surfaceContainerHigh = SurfaceEmphasis.light,
    surfaceContainerHighest = SurfaceEmphasis.light,
    surfaceContainerLow = SurfaceEmphasis.light,
    surfaceContainerLowest = SurfaceEmphasis.light,
)
