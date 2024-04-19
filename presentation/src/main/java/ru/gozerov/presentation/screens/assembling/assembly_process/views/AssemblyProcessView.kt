package ru.gozerov.presentation.screens.assembling.assembly_process.views

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.gozerov.domain.models.assembling.Container
import ru.gozerov.presentation.R
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun AssemblyProcessView(
    parentPaddingValues: PaddingValues,
    container: Container,
    step: Int,
    stepCount: Int
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = RoboticsGuideTheme.colors.primaryBackground
    ) { paddingValues ->
        Column {
            AssemblyProcessToolbar(step = step, stepCount = stepCount)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
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
            ContainerCard(id = container.component.id, name = container.component.name)
        }
    }

}

@Composable
internal fun AssemblyProcessToolbar(step: Int, stepCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(36.dp),
            tint = RoboticsGuideTheme.colors.primaryText,
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = null
        )
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            text = stringResource(id = R.string.step_is, step, stepCount)
        )
        Icon(
            modifier = Modifier
                .size(36.dp),
            tint = RoboticsGuideTheme.colors.primaryText,
            imageVector = Icons.Default.Settings,
            contentDescription = null
        )
    }

}

@Composable
internal fun ContainerCard(id: Int, name: String) {
    Card(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(contentColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_screw_left_24),
                contentDescription = null
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.ic_screw_right_24),
                contentDescription = null
            )
        }
        Text(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            text = name,
        )
        Text(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.simple_id_is, id)
        )
        Row(
            modifier = Modifier
                .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_screw_left_24),
                contentDescription = null
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.ic_screw_right_24),
                contentDescription = null
            )
        }
    }
}