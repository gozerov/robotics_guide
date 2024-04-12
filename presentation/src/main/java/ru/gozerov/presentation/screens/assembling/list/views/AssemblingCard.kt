package ru.gozerov.presentation.screens.assembling.list.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.gozerov.domain.models.assembling.SimpleAssembling
import ru.gozerov.presentation.R
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@Composable
fun AssemblingCard(
    assembling: SimpleAssembling,
    onCardClick: (id: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .background(
                RoboticsGuideTheme.colors.secondaryBackground,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
               onCardClick(assembling.id)
            },
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth()
        ) {
            Image(painter = painterResource(id = R.drawable.ic_screw_left_24), contentDescription = null)
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                textAlign = TextAlign.Center,
                text = assembling.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )
            Image(
                painter = painterResource(id = R.drawable.ic_screw_right_24),
                contentDescription = null
            )
        }

        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.BottomStart) {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                text = stringResource(id = R.string.assembling_id_is, assembling.id),
                color = RoboticsGuideTheme.colors.secondaryText,
                maxLines = 1
            )
        }
    }
}