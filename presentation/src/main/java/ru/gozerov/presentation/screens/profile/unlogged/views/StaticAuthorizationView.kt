package ru.gozerov.presentation.screens.profile.unlogged.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.gozerov.presentation.R
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@Composable
fun StaticAuthorizationView(onLogin: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_big_panda),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.saved_assemblings_available_auth),
            color = RoboticsGuideTheme.colors.outline
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .height(48.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .background(
                    color = RoboticsGuideTheme.colors.secondary,
                    shape = RoundedCornerShape(8.dp)
                ),
            colors = ButtonDefaults.buttonColors(containerColor = RoboticsGuideTheme.colors.secondary),
            onClick = onLogin
        ) {
            Text(
                text = stringResource(id = R.string.login),
                color = RoboticsGuideTheme.colors.surface
            )
        }
    }
}