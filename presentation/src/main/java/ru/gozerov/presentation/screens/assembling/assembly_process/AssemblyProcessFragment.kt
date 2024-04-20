package ru.gozerov.presentation.screens.assembling.assembly_process

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.gozerov.domain.models.assembling.AssemblyStep
import ru.gozerov.presentation.R
import ru.gozerov.presentation.screens.assembling.assembly_process.models.AssemblyProcessIntent
import ru.gozerov.presentation.screens.assembling.assembly_process.models.AssemblyProcessViewState
import ru.gozerov.presentation.screens.assembling.assembly_process.views.AssemblyProcessView
import ru.gozerov.presentation.screens.assembling.details.AssemblingDetailsFragment
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@AndroidEntryPoint
class AssemblyProcessFragment : Fragment() {

    private val viewModel by viewModels<AssemblyProcessViewModel>()

    private val args by navArgs<AssemblyProcessFragmentArgs>()

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
                    AssemblyProcessScreen()
                }
            }
        }
        return root
    }

    @Composable
    private fun AssemblyProcessScreen() {
        val viewState = viewModel.viewState.collectAsState().value
        val currentStep = remember { mutableStateOf<AssemblyStep?>(null) }
        LaunchedEffect(key1 = null) {
            viewModel.handleIntent(AssemblyProcessIntent.LoadStep(args.assemblingId))
        }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = RoboticsGuideTheme.colors.surfaceVariant
        ) { paddingValues ->
            currentStep.value?.run {
                AssemblyProcessView(
                    parentPaddingValues = paddingValues,
                    container = container,
                    step = step,
                    stepCount = stepCount,
                    onBackClick = {
                        viewModel.handleIntent(AssemblyProcessIntent.MoveOnNext(true))
                        findNavController().popBackStack()
                    },
                    onNextClick = {
                        viewModel.handleIntent(AssemblyProcessIntent.MoveOnNext(false))
                        if (isFinish) {
                            findNavController().popBackStack(R.id.assemblingDetailsFragment, false)
                        } else {
                            parentFragmentManager.setFragmentResult(AssemblingDetailsFragment.PROCESS_END_REQUEST_KEY, bundleOf())
                            val action =
                                AssemblyProcessFragmentDirections.actionAssemblyProcessFragmentSelf(
                                    args.assemblingId
                                )
                            findNavController().navigate(action)
                        }
                    }
                )
            }
        }
        when (viewState) {
            is AssemblyProcessViewState.Empty -> {}
            is AssemblyProcessViewState.LoadedStep -> {
                currentStep.value = viewState.step
            }

            is AssemblyProcessViewState.Error -> {}
        }
    }

}