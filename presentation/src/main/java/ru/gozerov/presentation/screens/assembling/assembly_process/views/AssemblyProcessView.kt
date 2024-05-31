package ru.gozerov.presentation.screens.assembling.assembly_process.views

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.gozerov.domain.models.assembling.AssemblingComponent
import ru.gozerov.presentation.R
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun AssemblyProcessView(
    parentPaddingValues: PaddingValues,
    component: AssemblingComponent,
    step: Int,
    stepCount: Int,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onSettingsClick: () -> Unit,
    isAudioPaused: Boolean,
    isAudioOff: Boolean,
    onPause: () -> Unit,
    onOff: () -> Unit,
    onRepeat: () -> Unit,
    onDismiss: () -> Unit,
    isMenuVisible: Boolean
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = RoboticsGuideTheme.colors.surfaceVariant
    ) { paddingValues ->
        Column {
            AssemblyProcessToolbar(
                step = step,
                stepCount = stepCount,
                onBackClick = onBackClick,
                onSettingsClick = onSettingsClick
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.TopEnd)
                    .padding(end = 16.dp)
            ) {
                if (isMenuVisible) {
                    DropdownMenu(
                        modifier = Modifier
                            .width(200.dp)
                            .background(RoboticsGuideTheme.colors.surfaceContainer)
                            .clip(RoundedCornerShape(8.dp)),
                        expanded = isMenuVisible,
                        onDismissRequest = {
                            onDismiss()
                        }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(id = if (isAudioOff) R.string.turn_on else R.string.turn_off),
                                    color = RoboticsGuideTheme.colors.primary
                                )
                            },
                            onClick = {
                                onOff()
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = if (isAudioOff) R.drawable.ic_volume_on_24 else R.drawable.ic_volume_off_24),
                                    contentDescription = null,
                                    tint = RoboticsGuideTheme.colors.primary
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(id = if (isAudioPaused) R.string.continue_audio else R.string.pause),
                                    color = RoboticsGuideTheme.colors.primary
                                )
                            },
                            onClick = {
                                onPause()
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = if (isAudioPaused) R.drawable.ic_play_arrow_24 else R.drawable.ic_pause_24),
                                    contentDescription = null,
                                    tint = RoboticsGuideTheme.colors.primary
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(id = R.string.repeat),
                                    color = RoboticsGuideTheme.colors.primary
                                )
                            },
                            onClick = {
                                onRepeat()
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_repeat_24),
                                    contentDescription = null,
                                    tint = RoboticsGuideTheme.colors.primary
                                )
                            }
                        )
                    }
                }
            }
            AssemblyProcessImageSection(component.photoUrl)
            ContainerCard(id = component.componentId, name = component.name)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.amount_is, component.amount),
                    color = RoboticsGuideTheme.colors.secondary,
                    fontSize = 24.sp
                )
            }
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RoboticsGuideTheme.colors.surfaceContainerHighest),
                    onClick = {
                        onBackClick()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.back),
                        color = RoboticsGuideTheme.colors.secondary
                    )
                }
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RoboticsGuideTheme.colors.surfaceContainerHighest),
                    onClick = {
                        onNextClick()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.next),
                        color = RoboticsGuideTheme.colors.primary
                    )
                }
            }
        }
    }

}

@Composable
internal fun AssemblyProcessToolbar(
    step: Int,
    stepCount: Int,
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onBackClick() }
        ) {
            Icon(
                modifier = Modifier
                    .size(36.dp),
                tint = RoboticsGuideTheme.colors.primary,
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = null
            )
        }

        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp),
            textAlign = TextAlign.Center,
            fontSize = 28.sp,
            color = RoboticsGuideTheme.colors.primary,
            text = stringResource(id = R.string.step_is, step, stepCount)
        )
        IconButton(
            onClick = { onSettingsClick() }
        ) {
            Icon(
                modifier = Modifier
                    .size(36.dp),
                tint = RoboticsGuideTheme.colors.primary,
                imageVector = Icons.Default.Settings,
                contentDescription = null
            )
        }
    }

}

@Composable
internal fun AssemblyProcessImageSection(imageUrl: String?) {
    if (imageUrl != null) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(240.dp)
                .clip(
                    shape = RoundedCornerShape(8.dp)
                ),
            contentScale = ContentScale.Crop
        )
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.padding(top = 48.dp),
                painter = painterResource(id = R.drawable.ic_pliers_left),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(36.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_pliers_center),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(36.dp))
            Image(
                modifier = Modifier.padding(top = 48.dp),
                painter = painterResource(id = R.drawable.ic_pliers_right),
                contentDescription = null
            )
        }
    }
}

@Composable
internal fun ContainerCard(id: Int, name: String) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = RoboticsGuideTheme.colors.surfaceContainer)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(
                    id = if (isSystemInDarkTheme()) R.drawable.ic_screw_left_dark_24 else R.drawable.ic_screw_left_24
                ),
                contentDescription = null
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(
                    id = if (isSystemInDarkTheme()) R.drawable.ic_screw_right_dark_24 else R.drawable.ic_screw_right_24
                ),
                contentDescription = null
            )
        }
        Text(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            text = name,
            color = RoboticsGuideTheme.colors.tertiary,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Text(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.simple_id_is, id),
            color = RoboticsGuideTheme.colors.outline,
            fontSize = 24.sp
        )
        Row(
            modifier = Modifier
                .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(
                    id = if (isSystemInDarkTheme()) R.drawable.ic_screw_left_dark_24 else R.drawable.ic_screw_left_24
                ),
                contentDescription = null
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(
                    id = if (isSystemInDarkTheme()) R.drawable.ic_screw_right_dark_24 else R.drawable.ic_screw_right_24
                ),
                contentDescription = null
            )
        }

    }
}