package ru.gozerov.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

@Composable
fun RoboticsGuideTheme(
    textSize: RoboticsGuideSize = RoboticsGuideSize.Medium,
    paddingSize: RoboticsGuideSize = RoboticsGuideSize.Medium,
    corners: RoboticsGuideCorners = RoboticsGuideCorners.Rounded,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) baseDarkPalette else baseLightPalette

    val typography = RoboticsGuideTypography(
        heading = TextStyle(
            fontSize = when (textSize) {
                RoboticsGuideSize.Small -> 16.sp
                RoboticsGuideSize.Medium -> 18.sp
                RoboticsGuideSize.Big -> 20.sp
            },
            fontWeight = FontWeight.Bold
        ),
        body = TextStyle(
            fontSize = when (textSize) {
                RoboticsGuideSize.Small -> 14.sp
                RoboticsGuideSize.Medium -> 16.sp
                RoboticsGuideSize.Big -> 18.sp
            },
            fontWeight = FontWeight.Normal
        ),
        toolbar = TextStyle(
            fontSize = when (textSize) {
                RoboticsGuideSize.Small -> 14.sp
                RoboticsGuideSize.Medium -> 16.sp
                RoboticsGuideSize.Big -> 18.sp
            },
            fontWeight = FontWeight.Medium
        ),
        caption = TextStyle(
            fontSize = when (textSize) {
                RoboticsGuideSize.Small -> 10.sp
                RoboticsGuideSize.Medium -> 12.sp
                RoboticsGuideSize.Big -> 14.sp
            }
        )
    )

    val shapes = RoboticsGuideShape(
        padding = when (paddingSize) {
            RoboticsGuideSize.Small -> 12.dp
            RoboticsGuideSize.Medium -> 16.dp
            RoboticsGuideSize.Big -> 20.dp
        },
        cornersStyle = when (corners) {
            RoboticsGuideCorners.Flat -> RoundedCornerShape(0.dp)
            RoboticsGuideCorners.Rounded -> RoundedCornerShape(8.dp)
        }
    )

    CompositionLocalProvider(
        LocalRoboticsGuideColors provides colors,
        LocalRoboticsGuideTypography provides typography,
        LocalRoboticsGuideShape provides shapes,
        content = content
    )
}