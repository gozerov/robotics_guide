package ru.gozerov.presentation.screens.profile.logged.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.gozerov.domain.models.login.UserProfile
import ru.gozerov.presentation.R
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@Composable
fun SuccessLoadedProfileView(
    userProfile: UserProfile,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        AsyncImage(
            modifier = Modifier
                .size(272.dp, 216.dp)
                .clip(RoundedCornerShape(8.dp)),
            model = userProfile.imageUrl,
            contentDescription = null,
            error = painterResource(id = R.drawable.ic_no_photo),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 24.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "${userProfile.lastName} ${userProfile.firstName}",
                    fontSize = 20.sp,
                    color = RoboticsGuideTheme.colors.onSurfaceVariant
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = mapRole(userProfile.role),
                    fontSize = 14.sp,
                    color = RoboticsGuideTheme.colors.onSurfaceVariant
                )
            }
            IconButton(
                modifier = Modifier
                    .padding(end = 24.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(RoboticsGuideTheme.colors.surfaceContainer)
                    .size(32.dp),
                onClick = {
                    onLogout()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logout_16),
                    contentDescription = null,
                    tint = RoboticsGuideTheme.colors.secondary
                )
            }
        }
    }
}

@Composable
private fun mapRole(role: Int): String {
    return when (role) {
        0 -> stringResource(id = R.string.creator)
        1 -> stringResource(id = R.string.admin)
        else -> stringResource(id = R.string.undefined)
    }
}