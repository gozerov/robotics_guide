package ru.gozerov.presentation.screens.profile.unlogged

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.gozerov.presentation.R
import ru.gozerov.presentation.screens.profile.unlogged.models.AuthorizationEffect
import ru.gozerov.presentation.screens.profile.unlogged.models.AuthorizationIntent
import ru.gozerov.presentation.screens.profile.unlogged.models.AuthorizationViewState
import ru.gozerov.presentation.screens.profile.unlogged.views.LoadingView
import ru.gozerov.presentation.screens.profile.unlogged.views.StaticAuthorizationView
import ru.gozerov.presentation.screens.tabs.TabsFragment
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@AndroidEntryPoint
class AuthorizationFragment : Fragment() {

    private val viewModel: AuthorizationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_composable, container, false)
        val composeView = root.findViewById<ComposeView>(R.id.composeView)
        activity?.supportFragmentManager?.setFragmentResultListener(
            REQUEST_KEY,
            viewLifecycleOwner
        ) { _, b ->
            val token = b.getString(KEY_TOKEN).toString()
            val id = b.getString(KEY_ID).toString()
            activity?.supportFragmentManager?.setFragmentResult(
                TabsFragment.REQUEST_KEY_AUTH,
                bundleOf()
            )
            viewModel.handleIntent(AuthorizationIntent.SaveLabToken(token, id))
        }

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
        val snackbarHostState = remember { SnackbarHostState() }

        val effect = viewModel.effect.collectAsState().value
        val errorMessage = stringResource(id = R.string.unknown_error)

        val viewState = viewModel.viewState.collectAsState().value

        when (effect) {
            is AuthorizationEffect.None -> {}
            is AuthorizationEffect.NavigateToProfile -> {
                findNavController().popBackStack()
            }

            is AuthorizationEffect.Error -> {
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
        ) {
            when (viewState) {
                is AuthorizationViewState.Empty -> {
                    StaticAuthorizationView(
                        onLogin = {
                            viewModel.handleIntent(AuthorizationIntent.Login)
                            val parentNavController =
                                (activity?.supportFragmentManager?.findFragmentById(R.id.globalFragmentContainer) as? NavHostFragment)?.navController
                            parentNavController?.navigate(R.id.action_tabsFragment_to_loginWebViewFragment)
                        }
                    )
                }

                is AuthorizationViewState.Loading -> {
                    LoadingView()
                }
            }
        }
    }

    companion object {

        const val REQUEST_KEY = "REQUEST"
        const val KEY_TOKEN = "TOKEN"
        const val KEY_ID = "ID"

    }

}