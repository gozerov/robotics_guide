package ru.gozerov.presentation.screens.assembling.check_availability.camera

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.assembling.TableData
import ru.gozerov.domain.models.assembling.TableTitle
import ru.gozerov.domain.models.assembling.toComponent
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.FragmentCheckAvailabilityBinding
import ru.gozerov.presentation.databinding.FragmentQrCameraBinding
import ru.gozerov.presentation.screens.assembling.check_availability.camera.models.CheckAvailabilityEffect
import ru.gozerov.presentation.screens.assembling.check_availability.camera.models.CheckAvailabilityIntent
import ru.gozerov.presentation.screens.assembling.check_availability.dialog_found.ComponentWithNeed
import ru.gozerov.presentation.screens.assembling.check_availability.dialog_found.adapter.ComponentListAdapter
import ru.gozerov.presentation.screens.assembling.check_availability.dialog_found.adapter.TableDividerItemDecoration


@AndroidEntryPoint
class CheckAvailabilityFragment : Fragment() {

    private lateinit var binding: FragmentCheckAvailabilityBinding

    private val viewModel: CheckAvailabilityViewModel by viewModels()

    private val args: CheckAvailabilityFragmentArgs by navArgs()

    private val adapter = ComponentListAdapter()

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckAvailabilityBinding.inflate(inflater, container, false)

        val bottomSheetBehavior =
            BottomSheetBehavior.from(binding.checkAvailabilityDialog.bottomSheet)

        bottomSheetBehavior.peekHeight = 100
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        parentFragmentManager.setFragmentResultListener(
            REQUEST_KEY, viewLifecycleOwner
        ) { _, _ -> viewModel.handleIntent(CheckAvailabilityIntent.SetCameraActive(true)) }

        binding.barCodeView.statusView.text = ""

        binding.barCodeView.barcodeView.decodeContinuous { result ->
            if (viewModel.isCameraActive && findNavController().currentDestination == findNavController()
                    .findDestination(R.id.checkAvailabilityFragment)
            ) {
                try {
                    val id = result.text.toInt()
                    viewModel.handleIntent(CheckAvailabilityIntent.AddComponent(id))
                    viewModel.handleIntent(CheckAvailabilityIntent.SetCameraActive(false))
                } catch (e: Exception) {
                    viewModel.handleIntent(CheckAvailabilityIntent.ShowError(result.text))
                }
            }
        }

        binding.checkAvailabilityDialog.showAllButton.setOnClickListener {
            viewModel.handleIntent(CheckAvailabilityIntent.ShowAllComponentsDialog())
        }

        binding.checkAvailabilityDialog.calculateButton.setOnClickListener {
            viewModel.handleIntent(CheckAvailabilityIntent.CalculateComponentsDiff(args.neededComponents.toList()))
        }

        binding.checkAvailabilityDialog.componentsList.adapter = adapter
        binding.checkAvailabilityDialog.componentsList.addItemDecoration(
            TableDividerItemDecoration(
                requireContext(),
                R.drawable.divider_component_list
            )
        )

        binding.qrRoot.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        is CheckAvailabilityEffect.None -> {
                            val components = args.neededComponents.map { component ->
                                ComponentWithNeed(component.toComponent(), false)
                            }
                            val data = mutableListOf<TableData>()
                            data.add(TableTitle(getString(R.string.detail_name)))
                            data.addAll(components)
                            adapter.data = data
                        }

                        is CheckAvailabilityEffect.ShowCheckAvailabilityDialog -> {
                            val components = args.neededComponents.map { component ->
                                val isNeeded =
                                    effect.components.any { it.id == component.componentId }
                                ComponentWithNeed(component.toComponent(), isNeeded)
                            }

                            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                            binding.checkAvailabilityDialog.componentsList.addItemDecoration(
                                TableDividerItemDecoration(
                                    requireContext(),
                                    R.drawable.divider_component_list
                                )
                            )
                            Handler(Looper.getMainLooper()).postDelayed({
                                viewModel.handleIntent(CheckAvailabilityIntent.SetCameraActive(true))
                            }, 500)
                            val data = mutableListOf<TableData>()
                            data.add(TableTitle(getString(R.string.detail_name)))
                            data.addAll(components)
                            adapter.data = data
                        }

                        is CheckAvailabilityEffect.NavigateToProcess -> {
                            val action =
                                CheckAvailabilityFragmentDirections.actionCheckAvailabilityFragmentToAssemblyProcessFragment(
                                    args.assemblingId
                                )
                            findNavController().navigate(action)
                        }

                        is CheckAvailabilityEffect.NavigateToLackOfComponentsDialog -> {
                            val action =
                                CheckAvailabilityFragmentDirections.actionCheckAvailabilityFragmentToLackOfComponentsDialog(
                                    args.assemblingId
                                )
                            findNavController().navigate(action)
                        }

                        is CheckAvailabilityEffect.ShowAllComponentsDialog -> {
                            val action =
                                CheckAvailabilityFragmentDirections.actionCheckAvailabilityFragmentToCompareTableDialog(
                                    effect.components.toTypedArray(),
                                    args.neededComponents
                                )
                            findNavController().navigate(action)
                        }

                        is CheckAvailabilityEffect.Error -> {
                            Snackbar.make(
                                requireView(),
                                getString(R.string.unknown_error), Snackbar.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }
        }
        binding.navUp.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    override fun onResume() {
        binding.barCodeView.resume()
        super.onResume()
    }

    override fun onPause() {
        binding.barCodeView.pause()
        super.onPause()
    }

    companion object {

        const val REQUEST_KEY = "checkRequestKey"

    }

}