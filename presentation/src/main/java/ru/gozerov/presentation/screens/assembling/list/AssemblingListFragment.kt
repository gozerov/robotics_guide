package ru.gozerov.presentation.screens.assembling.list

import android.content.Context
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.gozerov.domain.models.assembling.SimpleAssembling
import ru.gozerov.presentation.R
import ru.gozerov.presentation.screens.assembling.list.models.AssemblingListEffect
import ru.gozerov.presentation.screens.assembling.list.models.AssemblingListIntent
import ru.gozerov.presentation.screens.assembling.list.models.AssemblingListViewState
import ru.gozerov.presentation.screens.assembling.list.views.AssemblingListContainer
import ru.gozerov.presentation.screens.profile.unlogged.views.LoadingView
import ru.gozerov.presentation.screens.tabs.TabsFragment
import ru.gozerov.presentation.screens.tabs.TabsFragmentDirections
import ru.gozerov.presentation.shared.views.RequestCamera
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@AndroidEntryPoint
class AssemblingListFragment : Fragment() {

    private val viewModel: AssemblingListViewModel by viewModels()

    override fun onAttach(context: Context) {
        viewModel.handleIntent(AssemblingListIntent.CheckAuthorization)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_composable, container, false)
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
        val searchFieldState = rememberSaveable { mutableStateOf("") }

        val newAssemblings = rememberSaveable { mutableStateOf(emptyList<SimpleAssembling>()) }
        val allAssemblings = rememberSaveable { mutableStateOf(emptyList<SimpleAssembling>()) }
        val searchedAssemblings = rememberSaveable { mutableStateOf(emptyList<SimpleAssembling>()) }
        val categories = rememberSaveable { mutableStateOf(emptyList<String>()) }
        val currentCategory = rememberSaveable { mutableStateOf("") }

        val containerVisibility = rememberSaveable { mutableStateOf(false) }

        val viewState = viewModel.viewState.collectAsState().value
        val events = viewModel.effect.collectAsState().value

        LaunchedEffect(key1 = null) {
            viewModel.handleIntent(AssemblingListIntent.LoadCategories)
            viewModel.handleIntent(AssemblingListIntent.LoadAssemblings)
        }

        RequestCamera()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = RoboticsGuideTheme.colors.surfaceVariant
        ) { paddingValues ->
            if (containerVisibility.value) {
                AssemblingListContainer(
                    newAssemblings = newAssemblings.value,
                    allAssemblings = allAssemblings.value,
                    searchedAssemblings = searchedAssemblings.value,
                    categories = categories.value,
                    currentCategory = currentCategory,
                    parentPaddingValues = paddingValues,
                    searchFieldState = searchFieldState,
                    onSearchTextChanged = {
                        viewModel.handleIntent(
                            AssemblingListIntent.SearchAssembling(
                                it,
                                currentCategory.value
                            )
                        )
                    },
                    onCardClick = {
                        val action =
                            TabsFragmentDirections.actionTabsFragmentToAssemblingDetailsFragment(it)
                        requireActivity().findNavController(R.id.globalFragmentContainer)
                            .navigate(action)
                    },
                    onCategoryChanged = {
                        viewModel.handleIntent(
                            AssemblingListIntent.SearchAssembling(
                                searchFieldState.value,
                                currentCategory.value
                            )
                        )
                    }
                )
            } else
                LoadingView()
        }
        when (viewState) {
            is AssemblingListViewState.Empty -> {
                containerVisibility.value = false
            }

            is AssemblingListViewState.LoadedAssemblings -> {
                containerVisibility.value = true
                newAssemblings.value = viewState.newAssemblings
                allAssemblings.value = viewState.allAssemblings
            }

            is AssemblingListViewState.SearchedAssemblings -> {
                searchedAssemblings.value = viewState.assemblings
            }

            is AssemblingListViewState.Error -> {}
        }
        when (events) {
            is AssemblingListEffect.None -> {}
            is AssemblingListEffect.LoadedCategories -> {
                if (currentCategory.value.isEmpty())
                    currentCategory.value = events.categories[0]
                categories.value = events.categories
            }

            is AssemblingListEffect.NavigateToProfile -> {
                activity?.supportFragmentManager?.setFragmentResult(
                    TabsFragment.REQUEST_KEY_GO_TO_AUTH,
                    bundleOf()
                )
            }
        }
    }

}