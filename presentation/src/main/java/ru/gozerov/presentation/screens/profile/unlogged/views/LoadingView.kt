package ru.gozerov.presentation.screens.profile.unlogged.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@Composable
fun LoadingView() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            trackColor = RoboticsGuideTheme.colors.surfaceVariant,
            color = RoboticsGuideTheme.colors.secondary
        )
    }

}