package ru.gozerov.presentation.screens.assembling.list.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.gozerov.domain.models.assembling.SimpleAssembling
import ru.gozerov.presentation.R
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@Composable
fun SmallAssemblingCard(
    assembling: SimpleAssembling,
    onCardClick: (id: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .width(184.dp)
            .height(100.dp)
            .background(
                RoboticsGuideTheme.colors.surfaceContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                onCardClick(assembling.id)
            }
    ) {
        Text(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 8.dp),
            text = assembling.name,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = RoboticsGuideTheme.colors.primary,
            maxLines = 2
        )
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.BottomStart) {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                color = RoboticsGuideTheme.colors.outline,
                text = stringResource(id = R.string.assembling_id_is, assembling.id),
                maxLines = 1
            )
        }
    }

}