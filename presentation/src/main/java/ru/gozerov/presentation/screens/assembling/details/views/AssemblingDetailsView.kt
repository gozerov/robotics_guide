package ru.gozerov.presentation.screens.assembling.details.views

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.gozerov.domain.models.assembling.Assembling
import ru.gozerov.presentation.R
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AssemblingDetailsView(
    parentPaddingValues: PaddingValues,
    assembling: Assembling,
    onFavoriteClick: () -> Unit,
    onCollectClick: () -> Unit,
    onCheckAvailabilityClick: () -> Unit,
    onNavUpClick: () -> Unit
) {
    val navUpInteractionSource = remember { MutableInteractionSource() }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = RoboticsGuideTheme.colors.surfaceVariant
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
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
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 16.dp, start = 16.dp, end = 68.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp),
                        text = assembling.name,
                        fontSize = 22.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        color = RoboticsGuideTheme.colors.primary
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        color = RoboticsGuideTheme.colors.outlineVariant,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                item {
                    CellWithoutDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        firstText = stringResource(id = R.string.detail_name),
                        secondText = stringResource(id = R.string.detail_amount),
                        isBold = true
                    )
                    HorizontalDivider(
                        color = RoboticsGuideTheme.colors.outlineVariant
                    )
                }
                items(assembling.containers.size) {
                    val container = assembling.containers[it]
                    Column(
                        modifier = Modifier.height(56.dp)
                    ) {
                        CellWithoutDivider(
                            modifier = Modifier.fillMaxWidth(),
                            firstText = container.component.name,
                            secondText = stringResource(
                                id = R.string.amount_count,
                                container.amount
                            )
                        )
                        if (it != assembling.containers.size - 1) {
                            HorizontalDivider(
                                color = RoboticsGuideTheme.colors.outlineVariant
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 36.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(
                        id = if (isSystemInDarkTheme()) R.drawable.ic_nut_left_dark_48 else R.drawable.ic_nut_left_48
                    ),
                    contentDescription = null
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = RoboticsGuideTheme.colors.primary)) {
                            append(stringResource(R.string.that_assembling_repeated))
                        }
                        withStyle(
                            style = SpanStyle(
                                color = RoboticsGuideTheme.colors.primary,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(assembling.readyAmount.toString())
                        }
                        withStyle(style = SpanStyle(color = RoboticsGuideTheme.colors.primary)) {
                            append(stringResource(R.string._count))
                        }
                    }
                    Text(
                        color = RoboticsGuideTheme.colors.primary,
                        text = text
                    )
                }
                Image(
                    painter = painterResource(
                        id = if (isSystemInDarkTheme()) R.drawable.ic_nut_right_dark_48 else R.drawable.ic_nut_right_48
                    ),
                    contentDescription = null
                )
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                IconButton(
                    modifier = Modifier.size(48.dp),
                    colors = IconButtonDefaults.iconButtonColors(containerColor = RoboticsGuideTheme.colors.surfaceContainerHighest),
                    onClick = { onFavoriteClick() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_bookmark_border_24),
                        tint = RoboticsGuideTheme.colors.primary,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    modifier = Modifier
                        .height(48.dp)
                        .weight(1f)
                        .background(
                            color = RoboticsGuideTheme.colors.surfaceContainerHighest,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(containerColor = RoboticsGuideTheme.colors.surfaceContainerHighest),
                    onClick = { onCollectClick() }) {
                    Icon(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        painter = painterResource(id = R.drawable.ic_cutters_16),
                        contentDescription = null,
                        tint = RoboticsGuideTheme.colors.primary
                    )
                    Text(
                        text = stringResource(id = R.string.collect_components),
                        color = RoboticsGuideTheme.colors.primary
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .height(48.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .background(
                        color = RoboticsGuideTheme.colors.tertiary,
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = RoboticsGuideTheme.colors.tertiary),
                onClick = { onCheckAvailabilityClick() }) {
                Icon(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    painter = painterResource(id = R.drawable.ic_qr_16),
                    contentDescription = null,
                    tint = RoboticsGuideTheme.colors.surface
                )
                Text(
                    text = stringResource(id = R.string.check_components_availability),
                    color = RoboticsGuideTheme.colors.surface
                )
            }
        }
    }
}

@Composable
fun CellWithoutDivider(
    modifier: Modifier,
    firstText: String,
    secondText: String,
    isBold: Boolean = false
) {
    Row(modifier) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxHeight()
                .weight(5f),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                maxLines = 2,
                text = firstText,
                color = RoboticsGuideTheme.colors.onSurface,
                fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
            )
        }
        VerticalDivider(color = RoboticsGuideTheme.colors.outlineVariant)
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxHeight()
                .weight(2f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = secondText,
                color = RoboticsGuideTheme.colors.onSurface,
                fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}