package ru.gozerov.presentation.screens.profile.logged

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.gozerov.presentation.R
import ru.gozerov.presentation.screens.profile.logged.models.ProfileIntent
import ru.gozerov.presentation.screens.profile.logged.models.ProfileViewState
import ru.gozerov.presentation.screens.profile.logged.views.ErrorLoadedProfileView
import ru.gozerov.presentation.screens.profile.logged.views.SuccessLoadedProfileView
import ru.gozerov.presentation.screens.profile.unlogged.views.LoadingView
import ru.gozerov.presentation.screens.tabs.TabsFragment
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

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
                    ProfileScreen()
                }
            }
        }
        return root
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun ProfileScreen() {
        LaunchedEffect(key1 = null) {
            viewModel.handleIntent(ProfileIntent.LoadProfile)
        }

        val viewState = viewModel.viewState.collectAsState().value

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = RoboticsGuideTheme.colors.surfaceVariant,
        ) { _ ->
            when (viewState) {
                is ProfileViewState.Empty -> {
                    LoadingView()
                }

                is ProfileViewState.LoadedProfile -> {
                    SuccessLoadedProfileView(
                        userProfile = viewState.userProfile,
                        onLogout = {
                            CookieManager.getInstance().removeAllCookie()
                            activity?.supportFragmentManager?.setFragmentResult(
                                TabsFragment.REQUEST_KEY_LOG_OUT,
                                bundleOf()
                            )
                            viewModel.handleIntent(ProfileIntent.Logout)
                            findNavController().navigate(R.id.action_profile_to_authorizationFragment)
                        }
                    )
                }

                is ProfileViewState.Error -> {
                    ErrorLoadedProfileView()
                }
            }
        }
    }

}