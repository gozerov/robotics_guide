package ru.gozerov.presentation.screens.camera.edit.edit_component.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ru.gozerov.presentation.R
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@Composable
fun SuccessChangesDialog(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = RoboticsGuideTheme.colors.surfaceContainer,
                    shape = RoundedCornerShape(16.dp),
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(id = R.string.success_changes),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = RoboticsGuideTheme.colors.secondary
            )
            Image(
                painter = painterResource(id = R.drawable.ic_panda_head),
                contentDescription = null
            )
            Button(
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 24.dp)
                    .height(48.dp)
                    .width(120.dp)
                    .background(
                        color = RoboticsGuideTheme.colors.tertiary,
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = RoboticsGuideTheme.colors.tertiary),
                onClick = onDismiss
            ) {
                Text(
                    text = stringResource(id = R.string.ok),
                    color = RoboticsGuideTheme.colors.surface
                )
            }
        }

    }
}