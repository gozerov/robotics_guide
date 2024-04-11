package ru.gozerov.presentation.screens.assembling.list.views

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import ru.gozerov.domain.models.assembling.SimpleAssembling
import ru.gozerov.presentation.R
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@Composable
fun SmallAssemblingCard(assembling: SimpleAssembling) {
    Column(
        modifier = Modifier
            .width(184.dp)
            .height(80.dp)
            .background(RoboticsGuideTheme.colors.secondaryBackground, shape = RoundedCornerShape(8.dp)),
    ) {
        Text(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 4.dp),
            text = assembling.name,
            maxLines = 2
        )
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.BottomStart) {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                text = stringResource(id = R.string.assembling_id_is, assembling.id),
                maxLines = 1
            )
        }
    }

}