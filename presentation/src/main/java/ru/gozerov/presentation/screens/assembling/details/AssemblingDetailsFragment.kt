package ru.gozerov.presentation.screens.assembling.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.assembling.Assembling
import ru.gozerov.presentation.R
import ru.gozerov.presentation.screens.assembling.details.models.AssemblingDetailsIntent
import ru.gozerov.presentation.screens.assembling.details.models.AssemblingDetailsViewState
import ru.gozerov.presentation.screens.assembling.details.views.AssemblingDetailsView
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@AndroidEntryPoint
class AssemblingDetailsFragment : Fragment() {

    private val viewModel: AssemblingDetailsViewModel by viewModels()

    private val args: AssemblingDetailsFragmentArgs by navArgs()

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
                    AssemblingDetailsScreen()
                }
            }
        }
        return root
    }

    @Composable
    fun AssemblingDetailsScreen() {
        val snackbarHostState = remember { SnackbarHostState() }
        var showSnackbar: Boolean by remember { mutableStateOf(false) }

        parentFragmentManager.setFragmentResultListener(
            PROCESS_END_REQUEST_KEY,
            viewLifecycleOwner
        ) { _, _ ->
            showSnackbar = true
        }

        val assembling = remember { mutableStateOf<Assembling?>(null) }
        val viewState = viewModel.viewState.collectAsState().value

        LaunchedEffect(key1 = null) {
            viewModel.handleIntent(AssemblingDetailsIntent.LoadAssembling(args.id))
        }

        LaunchedEffect(key1 = showSnackbar) {
            if (showSnackbar) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.handleIntent(AssemblingDetailsIntent.LoadAssembling(args.id))
                    snackbarHostState.showSnackbar(
                        message = "Вы повторили сборку",
                        duration = SnackbarDuration.Short
                    )
                }
                showSnackbar = false
            }
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = RoboticsGuideTheme.colors.surfaceVariant,
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { paddingValues ->

            assembling.value?.let {
                AssemblingDetailsView(
                    parentPaddingValues = paddingValues,
                    assembling = it,
                    onFavoriteClick = {},
                    onCollectClick = {
                        val action = AssemblingDetailsFragmentDirections
                            .actionAssemblingDetailsFragmentToAssemblyProcessFragment(it.id)
                        findNavController().navigate(action)
                    },
                    onCheckAvailabilityClick = {
                        findNavController().navigate(R.id.action_assemblingDetailsFragment_to_checkAvailabilityFragment)
                    },
                    onNavUpClick = {
                        findNavController().popBackStack()
                    }
                )
            }
        }
        when (viewState) {
            is AssemblingDetailsViewState.Empty -> {}
            is AssemblingDetailsViewState.LoadedAssembling -> {
                assembling.value = viewState.assembling
            }

            is AssemblingDetailsViewState.Error -> {}
        }
    }

    companion object {

        const val PROCESS_END_REQUEST_KEY = "processRequestKey"

    }

}