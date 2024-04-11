package ru.gozerov.presentation.screens.tabs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.get
import androidx.navigation.ui.setupWithNavController
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.FragmentTabsBinding

class TabsFragment: Fragment() {

    private lateinit var binding: FragmentTabsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val childNavController = (childFragmentManager.findFragmentById(R.id.localFragmentContainer) as NavHostFragment).navController
        binding.bottomNavView.setupWithNavController(childNavController)
        (childNavController.graph[R.id.nav_profile] as NavGraph).setStartDestination(R.id.authorizationFragment)
    }


}