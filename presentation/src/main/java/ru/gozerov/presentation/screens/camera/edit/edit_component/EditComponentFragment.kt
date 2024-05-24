package ru.gozerov.presentation.screens.camera.edit.edit_component

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.gozerov.presentation.R
import ru.gozerov.presentation.screens.camera.edit.edit_component.dialog.SuccessChangesDialog
import ru.gozerov.presentation.screens.camera.edit.edit_component.models.EditComponentEffect
import ru.gozerov.presentation.screens.camera.edit.edit_component.models.EditComponentIntent
import ru.gozerov.presentation.shared.views.NavUpView
import ru.gozerov.presentation.shared.views.RequestImageStorage
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@AndroidEntryPoint
class EditComponentFragment : Fragment() {

    private val viewModel: EditComponentViewModel by viewModels()

    private val args: EditComponentFragmentArgs by navArgs()

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
                    EditComponentScreen()
                }
            }
        }
        return root
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun EditComponentScreen() {
        val snackbarHostState = remember { SnackbarHostState() }
        val errorMessage = stringResource(id = R.string.unknown_error)
        val componentName = remember { mutableStateOf(args.name) }
        val isLoading: Boolean by remember { mutableStateOf(false) }

        val imageUri = remember { mutableStateOf<Uri?>(null) }
        val context = LocalContext.current

        val launchStoragePermissionState = remember { mutableStateOf(false) }
        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
                imageUri.value = uri
                launchStoragePermissionState.value = false
            }

        if (launchStoragePermissionState.value) {
            RequestImageStorage {
                launcher.launch("image/*")
            }
        }

        val effect = viewModel.effect.collectAsState().value

        when (effect) {
            is EditComponentEffect.None -> {}
            is EditComponentEffect.ShowDialog -> {
                SuccessChangesDialog {
                    activity?.findNavController(R.id.globalFragmentContainer)?.popBackStack()
                }
            }

            is EditComponentEffect.Error -> {
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
                            painter = painterResource(id = R.drawable.ic_edit_pliers),
                            contentDescription = null
                        )
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth()
                        .height(64.dp),
                    value = componentName.value,
                    onValueChange = {
                        componentName.value = it
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_bolt),
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
                    text = stringResource(id = R.string.input_component_name),
                    modifier = Modifier.padding(start = 40.dp, top = 4.dp),
                    color = RoboticsGuideTheme.colors.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = RoboticsGuideTheme.colors.outlineVariant,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                        painter = painterResource(id = R.drawable.ic_camera),
                        tint = RoboticsGuideTheme.colors.outline,
                        contentDescription = null
                    )
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = stringResource(id = R.string.take_photo),
                            fontSize = 18.sp,
                            color = RoboticsGuideTheme.colors.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        if (imageUri.value == null) {
                            Button(
                                modifier = Modifier
                                    .height(48.dp)
                                    .background(
                                        color = RoboticsGuideTheme.colors.surfaceContainerHighest,
                                        shape = RoundedCornerShape(8.dp)
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = RoboticsGuideTheme.colors.surfaceContainerHighest
                                ),
                                onClick = {
                                    launchStoragePermissionState.value = false
                                    launchStoragePermissionState.value = true
                                }
                            ) {
                                Text(
                                    text = stringResource(id = R.string.add_photo),
                                    color = RoboticsGuideTheme.colors.primary
                                )
                            }
                        } else {
                            val imagePath =
                                imageUri.value!!.lastPathSegment + context.contentResolver.getType(
                                    imageUri.value!!
                                )
                                    ?.replace("image/", ".")
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier
                                        .background(
                                            color = RoboticsGuideTheme.colors.surfaceContainerHighest,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .padding(8.dp)
                                        .weight(1f),
                                    text = imagePath,
                                    color = RoboticsGuideTheme.colors.primary
                                )
                                IconButton(
                                    onClick = {
                                        imageUri.value = null
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_delete_outline_24),
                                        contentDescription = null,
                                        tint = RoboticsGuideTheme.colors.outline
                                    )
                                }
                            }
                        }
                    }
                }
            }
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
                        viewModel.handleIntent(
                            EditComponentIntent.SaveChanges(args.id, componentName.value, imageUri.value)
                        )
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