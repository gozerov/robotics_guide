package ru.gozerov.presentation.screens.camera.edit.edit_container

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.gozerov.presentation.R
import ru.gozerov.presentation.screens.camera.edit.edit_component.dialog.SuccessChangesDialog
import ru.gozerov.presentation.screens.camera.edit.edit_container.models.EditContainerEffect
import ru.gozerov.presentation.screens.camera.edit.edit_container.models.EditContainerIntent
import ru.gozerov.presentation.shared.views.NavUpView
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@AndroidEntryPoint
class EditContainerFragment : Fragment() {

    private val viewModel: EditContainerViewModel by viewModels()

    private val args: EditContainerFragmentArgs by navArgs()

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
                    EditContainerScreen()
                }
            }
        }
        return root
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun EditContainerScreen() {
        val snackbarHostState = remember { SnackbarHostState() }
        val errorMessage = stringResource(id = R.string.unknown_error)

        val containerNumber = remember { mutableStateOf(args.container.number) }
        val componentId = remember { mutableStateOf(args.container.componentId.toString()) }
        val room = remember { mutableStateOf(args.container.room) }

        var isLoading: Boolean by remember { mutableStateOf(false) }

        val effect = viewModel.effect.collectAsState().value

        when (effect) {
            is EditContainerEffect.None -> {}
            is EditContainerEffect.ShowDialog -> {
                SuccessChangesDialog {
                    activity?.findNavController(R.id.globalFragmentContainer)?.popBackStack()
                }
            }

            is EditContainerEffect.Error -> {
                LaunchedEffect(key1 = null) {
                    snackbarHostState.showSnackbar(errorMessage)
                }
            }
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = RoboticsGuideTheme.colors.surfaceVariant,
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { _ ->
            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    NavUpView {
                        findNavController().popBackStack()
                    }
                    Box(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_green_container),
                            contentDescription = null
                        )
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
                ComponentTextField(
                    text = containerNumber,
                    isLoading = isLoading,
                    hintRes = R.string.input_component_name,
                    trailingIconId = R.drawable.ic_bolt
                )
                ComponentTextField(
                    text = componentId,
                    isLoading = isLoading,
                    hintRes = R.string.input_component_id,
                    R.drawable.ic_lattice_18
                )
                ComponentTextField(
                    text = room,
                    isLoading = isLoading,
                    hintRes = R.string.input_room,
                    R.drawable.ic_box_18
                )
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Button(
                        modifier = Modifier
                            .padding(bottom = 24.dp)
                            .height(48.dp)
                            .background(
                                color = RoboticsGuideTheme.colors.tertiary,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(containerColor = RoboticsGuideTheme.colors.tertiary),
                        onClick = {
                            isLoading = true
                            componentId.value.toIntOrNull()?.let { id ->
                                viewModel.handleIntent(
                                    EditContainerIntent.SaveChanges(
                                        args.container.copy(
                                            number = containerNumber.value,
                                            room = room.value,
                                            componentId = id
                                        )
                                    )
                                )
                            } ?: viewModel.handleIntent(EditContainerIntent.ShowError())
                        }
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(end = 8.dp),
                            painter = painterResource(id = R.drawable.ic_save_16),
                            contentDescription = null,
                            tint = RoboticsGuideTheme.colors.surface
                        )
                        Text(
                            text = stringResource(id = R.string.save),
                            color = RoboticsGuideTheme.colors.surface
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun ComponentTextField(
        text: MutableState<String>,
        isLoading: Boolean,
        @StringRes hintRes: Int,
        @DrawableRes trailingIconId: Int
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .height(64.dp),
            value = text.value,
            onValueChange = {
                text.value = it
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = trailingIconId),
                    tint = RoboticsGuideTheme.colors.outline,
                    contentDescription = null
                )
            },
            enabled = !isLoading,
            singleLine = true,
            textStyle = RoboticsGuideTheme.typography.body,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = RoboticsGuideTheme.colors.surfaceVariant,
                unfocusedContainerColor = RoboticsGuideTheme.colors.surfaceVariant,
                focusedIndicatorColor = RoboticsGuideTheme.colors.outlineVariant,
                unfocusedIndicatorColor = RoboticsGuideTheme.colors.outlineVariant,
                cursorColor = RoboticsGuideTheme.colors.secondary,
                focusedTextColor = RoboticsGuideTheme.colors.outline,
                unfocusedTextColor = RoboticsGuideTheme.colors.outline,
                disabledContainerColor = RoboticsGuideTheme.colors.surfaceVariant,
                disabledLabelColor = RoboticsGuideTheme.colors.outline,
                disabledIndicatorColor = RoboticsGuideTheme.colors.outlineVariant,
                disabledTextColor = RoboticsGuideTheme.colors.outline
            )
        )
        Text(
            text = stringResource(hintRes),
            modifier = Modifier.padding(start = 40.dp, top = 4.dp),
            color = RoboticsGuideTheme.colors.onSurfaceVariant
        )
    }

}