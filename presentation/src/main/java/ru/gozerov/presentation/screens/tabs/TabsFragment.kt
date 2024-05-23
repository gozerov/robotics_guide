package ru.gozerov.presentation.screens.tabs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.get
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.FragmentTabsBinding
import ru.gozerov.presentation.screens.tabs.models.TabsEffect
import ru.gozerov.presentation.screens.tabs.models.TabsIntent

@AndroidEntryPoint
class TabsFragment : Fragment() {

    private lateinit var binding: FragmentTabsBinding

    private val viewModel: TabsViewModel by viewModels()

    override fun onAttach(context: Context) {
        viewModel.handleIntent(TabsIntent.CheckIsAuthorized)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val childNavController =
            (childFragmentManager.findFragmentById(R.id.localFragmentContainer) as NavHostFragment).navController

        activity?.supportFragmentManager?.setFragmentResultListener(REQUEST_KEY_AUTH, viewLifecycleOwner) { key, _ ->
            if (key == REQUEST_KEY_AUTH)
                (childNavController.graph[R.id.nav_profile] as NavGraph).setStartDestination(R.id.profile)
            else if (key == REQUEST_KEY_LOG_OUT)
                (childNavController.graph[R.id.nav_profile] as NavGraph).setStartDestination(R.id.authorizationFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is TabsEffect.CheckResult -> {
                        binding.bottomNavView.setupWithNavController(childNavController)

                        val profileStart =
                            if (effect.isAuthorized) R.id.profile else R.id.authorizationFragment
                        (childNavController.graph[R.id.nav_profile] as NavGraph).setStartDestination(
                            profileStart
                        )
                    }
                }
            }
        }
    }

    companion object {

        const val REQUEST_KEY_AUTH = "requestKeyAuth"
        const val REQUEST_KEY_LOG_OUT = "requestKeyLogOut"

    }

}