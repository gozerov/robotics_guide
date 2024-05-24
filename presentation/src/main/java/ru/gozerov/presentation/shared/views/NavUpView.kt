package ru.gozerov.presentation.shared.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@Composable
fun NavUpView(
    onNavUpClick: () -> Unit
) {
    val navUpInteractionSource = remember { MutableInteractionSource() }
    Icon(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp)
            .size(36.dp)
            .clickable(navUpInteractionSource, null) {
                onNavUpClick()
            },
        tint = RoboticsGuideTheme.colors.primary,
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = null
    )
}