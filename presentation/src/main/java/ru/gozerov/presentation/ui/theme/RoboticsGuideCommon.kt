package ru.gozerov.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class RoboticsGuideColors(
    val surfaceVariant: Color,
    val surfaceContainer: Color,
    val surfaceContainerHigh: Color,
    val primary: Color,
    val outline: Color,
    val secondary: Color,
    val outlineVariant: Color,
    val primaryContainer: Color,
    val onSurface: Color,
    val surface: Color,
    val tertiary: Color,
    val surfaceContainerHighest: Color,
    val onSurfaceVariant: Color,
    val dividerColor: Color,
    val secondaryContainer: Color,
)

data class RoboticsGuideTypography(
    val heading: TextStyle,
    val body: TextStyle,
    val toolbar: TextStyle,
    val caption: TextStyle
)

data class RoboticsGuideShape(
    val padding: Dp,
    val cornersStyle: Shape
)

object RoboticsGuideTheme {
    val colors: RoboticsGuideColors
        @Composable
        get() = LocalRoboticsGuideColors.current

    val typography: RoboticsGuideTypography
        @Composable
        get() = LocalRoboticsGuideTypography.current

    val shapes: RoboticsGuideShape
        @Composable
        get() = LocalRoboticsGuideShape.current
}

enum class RoboticsGuideSize {
    Small, Medium, Big
}

enum class RoboticsGuideCorners {
    Flat, Rounded
}

val LocalRoboticsGuideColors = staticCompositionLocalOf<RoboticsGuideColors> {
    error("No colors provided")
}

val LocalRoboticsGuideTypography = staticCompositionLocalOf<RoboticsGuideTypography> {
    error("No font provided")
}

val LocalRoboticsGuideShape = staticCompositionLocalOf<RoboticsGuideShape> {
    error("No shapes provided")
}