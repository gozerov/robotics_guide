package ru.gozerov.presentation.screens.profile.unlogged

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import ru.gozerov.presentation.R
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

class AuthorizationFragment: Fragment(R.layout.fragment_authorization) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_composable, container, false)
        val composeView = root.findViewById<ComposeView>(R.id.composeView)
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                RoboticsGuideTheme {
                    AuthorizationScreen()
                }
            }
        }
        return root
    }
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun AuthorizationScreen() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = RoboticsGuideTheme.colors.surfaceVariant,
        ) { paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center,
                    text = "Сохранённые сборки доступны только авторизованным пользователям",
                    color = RoboticsGuideTheme.colors.outline
                )
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
                    onClick = {  }) {
                    Text(
                        text = stringResource(id = R.string.login),
                        color = RoboticsGuideTheme.colors.surface
                    )
                }
            }
        }
    }

}