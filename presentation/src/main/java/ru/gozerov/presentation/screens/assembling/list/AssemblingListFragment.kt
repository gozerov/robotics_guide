package ru.gozerov.presentation.screens.assembling.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import ru.gozerov.presentation.R
import ru.gozerov.presentation.screens.assembling.list.views.AssemblingListView
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

class AssemblingListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_assembling_list, container, false)
        val composeView = root.findViewById<ComposeView>(R.id.composeView)
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                RoboticsGuideTheme {
                    AssemblingListScreen()
                }
            }
        }
        return root
    }

    @Composable
    fun AssemblingListScreen() {
        val searchFieldState = remember { mutableStateOf("") }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = RoboticsGuideTheme.colors.primaryBackground
        ) { paddingValues ->
            AssemblingListView(
                parentPaddingValues = paddingValues,
                searchFieldState = searchFieldState,
                onSearchTextChanged = {}
            )
        }
    }

}