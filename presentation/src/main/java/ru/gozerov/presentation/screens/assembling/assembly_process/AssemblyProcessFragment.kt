package ru.gozerov.presentation.screens.assembling.assembly_process

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import ru.gozerov.domain.models.assembling.Component
import ru.gozerov.domain.models.assembling.Container
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.FragmentComposableBinding
import ru.gozerov.presentation.screens.assembling.assembly_process.views.AssemblyProcessView
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

class AssemblyProcessFragment: Fragment() {

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
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = RoboticsGuideTheme.colors.primaryBackground
        ) { paddingValues ->
            AssemblyProcessView(
                parentPaddingValues = paddingValues,
                container = Container(0, Component(0, "Винт М3-20", null, "А-415"), 10),
                step = 1,
                stepCount = 9
            )
        }
    }

}